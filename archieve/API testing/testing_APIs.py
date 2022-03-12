from pycoingecko import CoinGeckoAPI
cg = CoinGeckoAPI()
print(cg.get_price(ids='bitcoin', vs_currencies='usd'))
# cg.ping()
# print(cg.get_coins_list())
print(cg.get_coin_history_by_id(ids='bitcoin', vs_currencies='usd'))