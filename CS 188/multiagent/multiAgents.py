# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from math import inf
from statistics import mean
from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood() #Food grid
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"

        #Distance from Pacman to the Ghost

        g_positions = [ghost.getPosition() for ghost in newGhostStates]
        g_distances = [manhattanDistance(newPos, ghostPos) for ghostPos in g_positions]
        c_ghost = min(g_distances)
        #cg_index = [index for index in range(len(g_distances)) if g_distances[index] == c_ghost][0]
        g_near = len([d for d in g_distances if d < 3])

        f_pos = newFood.asList()
        f_dist = [0]

        for pos in f_pos:
            f_dist.append(manhattanDistance(newPos, pos))
        
        avg_dist = mean(f_dist)
        num_food = len(f_pos)
        f_score = (newFood.height - 1) * (newFood.width - 1) - num_food

        score = successorGameState.getScore()

        for idx in range(len(g_distances)):
            if (newScaredTimes[idx] > 0):
                if (newScaredTimes[idx] > g_distances[idx]) & (g_distances[idx] == c_ghost):
                    return score + (100 - c_ghost) * 5 + g_near * 2 - avg_dist * 5 + f_score * 2

                return score + c_ghost - g_near - avg_dist * 5 + f_score * 2

        if c_ghost > 2 and g_near < 3:
            return score + c_ghost - g_near - avg_dist * 2 + f_score * 1.5
        
        if c_ghost < 3:
            return score + c_ghost * 5 - g_near - avg_dist + f_score
        
        return score + c_ghost - g_near - avg_dist + f_score * 1.1



        """

        g_positions = [ghost.getPosition() for ghost in newGhostStates]
        g_distances = [manhattanDistance(newPos, ghostPos) for ghostPos in g_positions]
        c_ghost = min(g_distances)
        cg_index = [index for index in range(len(g_distances)) if g_distances[index] == c_ghost][0]
        g_near = len([d for d in g_distances if d < 3])

        f_pos = newFood.asList()
        f_dist = [0]

        for pos in f_pos:
            f_dist.append(manhattanDistance(newPos, pos))
        
        avg_dist = mean(f_dist)
        num_food = len(f_pos)

        score = successorGameState.getScore()

        for idx in range(len(g_distances)):
            if (newScaredTimes[idx] > g_distances[idx]) & (g_distances[idx] == c_ghost):
                return score - c_ghost * 5 + g_near - avg_dist - num_food

        if c_ghost > 2 and g_near < 3:
            return score + c_ghost - g_near - avg_dist * 2 - num_food
        
        if c_ghost < 3:
            return score + c_ghost * 3 - g_near - avg_dist - num_food
        
        return score + c_ghost * 2 - g_near - avg_dist - num_food

        """



def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    
    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"

        def minimax(self, gameState, depth, agentIndex):
            
            if depth == 0:
                return self.evaluationFunction(gameState)
            
            if agentIndex == 0:
                max_score = -inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)

                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = minimax(self, successor, depth, agentIndex+1)

                    max_score = max(max_score, score)

                return max_score

            if agentIndex == gameState.getNumAgents() - 1:
                min_score = inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)

                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = minimax(self, successor, depth-1, 0)

                    min_score = min(min_score, score)

                return min_score
            
            else:
                min_score = inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)
                    
                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = minimax(self, successor, depth, agentIndex+1)
                    
                    min_score = min(min_score, score)

                return min_score

        
        best_score = -inf
        best_action = None

        for action in gameState.getLegalActions(0):

            successor = gameState.generateSuccessor(0, action)

            if successor.isWin() or successor.isLose():
                score = self.evaluationFunction(successor)
            else:
                score = minimax(self, successor, self.depth, 1)

            if best_score <= score:
                best_score = score
                best_action = action

        return best_action





class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"

        alpha = -inf
        beta = inf

        def alphabeta(self, gameState, depth, agentIndex, alpha, beta):

            if depth == 0:
                return self.evaluationFunction(gameState)
            
            if agentIndex == 0:
                max_score = -inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)

                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)

                    else:
                        score = alphabeta(self, successor, depth, agentIndex+1, alpha, beta)

                    if beta < score:
                        return score

                    if score > max_score:
                        max_score = score
                        alpha = max(max_score, alpha)
                    
                    #print("alpna : ", alpha, " | ", "beta : ", beta, " | ", "max : ", max_score)

                return max_score

            if agentIndex == gameState.getNumAgents() - 1:
                min_score = inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)

                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                        
                    else:
                        
                        score = alphabeta(self, successor, depth-1, 0, alpha, beta)

                    if alpha > score:
                        return score

                    if score < min_score:
                        min_score = score
                        beta = min(beta, min_score)

                    #print("alpna : ", alpha, " | ", "beta : ", beta, " | ", "min : ", min_score)

                return min_score
            
            else:
                min_score = inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)
                    
                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = alphabeta(self, successor, depth, agentIndex+1, alpha, beta)
                    
                    if alpha > score:
                        return score

                    if score < min_score:
                        min_score = score
                        beta = min(beta, min_score)

                    #print("alpna : ", alpha, " | ", "beta : ", beta, " | ", "min : ", min_score)
                    
                return min_score


        best_score = -inf
        best_action = None
        
        for action in gameState.getLegalActions(0):

            successor = gameState.generateSuccessor(0, action)

            if successor.isWin() or successor.isLose():
                score = self.evaluationFunction(successor)

            else:
                score = alphabeta(self, successor, self.depth, 1, alpha, beta)

            if beta < score:
                return best_score

            if score > best_score:
                best_score = score
                best_action = action
                alpha = max(best_score, alpha)

        #print()
        return best_action

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"

        def expect(self, gameState, depth, agentIndex):
            
            if depth == 0:
                return self.evaluationFunction(gameState)
            
            if agentIndex == 0:
                max_score = -inf
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)

                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = expect(self, successor, depth, agentIndex+1)

                    max_score = max(max_score, score)

                return max_score

            if agentIndex == gameState.getNumAgents() - 1:
                scores = []
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)

                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = expect(self, successor, depth-1, 0)

                    scores.append(score)

                return mean(scores)
            
            else:
                scores = []
                for action in gameState.getLegalActions(agentIndex):
                    successor = gameState.generateSuccessor(agentIndex, action)
                    
                    if successor.isWin() or successor.isLose():
                        score = self.evaluationFunction(successor)
                    else:
                        score = expect(self, successor, depth, agentIndex+1)
                    
                    scores.append(score)

                return mean(scores)

        
        best_score = -inf
        best_action = None

        for action in gameState.getLegalActions(0):

            successor = gameState.generateSuccessor(0, action)

            if successor.isWin() or successor.isLose():
                score = self.evaluationFunction(successor)
            else:
                score = expect(self, successor, self.depth, 1)

            if best_score <= score:
                best_score = score
                best_action = action

        return best_action

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    #Distance from Pacman to the Ghost

    newPos = currentGameState.getPacmanPosition()
    newFood = currentGameState.getFood()
    newGhostStates = currentGameState.getGhostStates()
    newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

    g_positions = [ghost.getPosition() for ghost in newGhostStates]
    g_distances = [manhattanDistance(newPos, ghostPos) for ghostPos in g_positions]
    c_ghost = min(g_distances)
    #cg_index = [index for index in range(len(g_distances)) if g_distances[index] == c_ghost][0]
    g_near = len([d for d in g_distances if d < 3])

    f_pos = newFood.asList()
    f_dist = [0]

    for pos in f_pos:
        f_dist.append(manhattanDistance(newPos, pos))
    
    avg_dist = mean(f_dist)
    num_food = len(f_pos)
    f_score = (newFood.height - 1) * (newFood.width - 1) - num_food

    score = currentGameState.getScore()

    for idx in range(len(g_distances)):
        if (newScaredTimes[idx] > 0):
            if (newScaredTimes[idx] > g_distances[idx]) & (g_distances[idx] == c_ghost):
                return score + (100 - c_ghost) * 5 + g_near * 2 - avg_dist * 5 + f_score * 2

            return score + c_ghost - g_near - avg_dist * 5 + f_score * 2

    if c_ghost > 2 and g_near < 3:
        return score + c_ghost - g_near - avg_dist * 2 + f_score * 1.5
    
    if c_ghost < 3:
        return score + c_ghost * 5 - g_near - avg_dist + f_score
    
    return score + c_ghost - g_near - avg_dist + f_score * 1.1

# Abbreviation
better = betterEvaluationFunction
