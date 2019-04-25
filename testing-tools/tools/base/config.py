"""
Global configs
"""
import os
from configparser import ConfigParser

APP_KEY = None
APP_ID = None
APP_MASTER_KEY = None
ROUTER_URL = None
ROUTER_INTERNAL_URL = None
CLIENT_UA = None
TEST_WITH_HOOK = False
FEATURE = None
CONFIG_BITMAP = 0xFFFF
DEFAULT_PROTOCOL = 'json.1'
USE_APP_ROUTER_ADDR = False

_UNSET = object()


def create_config_fetcher(env):
    cfg = ConfigParser()
    cfg.read('%s/config.ini' % os.getcwd())

    def fetch_by_key(key, fallback=_UNSET):
        if os.environ.get(key):
            return os.environ.get(key)
        else:
            if fallback == _UNSET:
                return cfg.get(env, key)
            else:
                return cfg.get(env, key, fallback=fallback)
    return fetch_by_key


def init_config(env):
    global APP_KEY
    global APP_ID
    global APP_MASTER_KEY
    global ROUTER_URL
    global ROUTER_INTERNAL_URL
    global ROUTER_AUTH_KEY
    global CLIENT_UA
    global TEST_WITH_HOOK
    global FEATURE
    global CONFIG_BITMAP
    global USE_APP_ROUTER_ADDR

    fetch_config = create_config_fetcher(env)

    APP_ID = fetch_config("APPID")
    APP_KEY = fetch_config('APPKEY')
    APP_MASTER_KEY = fetch_config('APP_MASTER_KEY')
    CLIENT_UA = 'GameTest/1.0'

    ROUTER_INTERNAL_URL = fetch_config('GAME_ROUTER_INTERNAL_URL')
    ROUTER_AUTH_KEY = fetch_config('GAME_ROUTER_AUTH_KEY')
    TEST_WITH_HOOK = fetch_config('TEST_WITH_HOOK', fallback="False") == "True"
    FEATURE = fetch_config("FEATURE", fallback=None)
    CONFIG_BITMAP = int(fetch_config(
        "CONFIG_BITMAP", fallback="0xFFFF"), base=16)
    USE_APP_ROUTER_ADDR = fetch_config(
        'USE_APP_ROUTER', fallback="False") == "True"
    if USE_APP_ROUTER_ADDR:
        ROUTER_URL = "https://%s.%s" % (
            APP_ID[0:8], fetch_config('GAME_ROUTER_URL'))
    else:
        ROUTER_URL = fetch_config('GAME_ROUTER_URL')