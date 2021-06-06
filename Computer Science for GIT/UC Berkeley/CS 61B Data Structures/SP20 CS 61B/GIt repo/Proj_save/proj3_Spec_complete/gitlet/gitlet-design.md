# Gitlet Design Document

**Name**: Kidong Kim

## Classes and Data Structures


Gitlet Repository contains Commit, Stage, Blobs.

* Repository class whose constructor we call when the init command is invoked

Branch
* Commit tree → Modified LinkedList data structure
* Blobs → Set data structure to prevent duplicates
* Staging areas → Map<File, blob> for both addition and removal



### Commit

Contains metadata of the file with its content

#### Instance variables

* String Message - Commit message 
* Timestamp Timestamp - Time at which commit is created
* Commit Parent_1 and Parent_2  - Pointer to its parent commits
* String commitLog - Commit log
* String Name - Name of the file (SHA1 ID)
* String Branch - Current working branch of the commit

### Blob

Contains of contents of the file with its name

#### Instance variables

* Sting Name - Name of the file (SHA1 ID)
* byte[] Contents - Contents of the file in a stream of bytes


### Stage

Contains of files associated with their blobs

#### Instance variables

* Map<String files, String blobs> contents - Contents of the stage


### Commands

Interprets gitlet commands passed into gitlet.Main

* Use Switch phrase for various options

#### Instance variables

* HashSet<String> _commands - Set of commands associated with its operation
* String _command - String of the command
* String[] _operand - Array of operand Strings 

##### Commands list
* Commit - Would have to have access to staging area



## Algorithms



## Persistence

