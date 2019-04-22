from tools.base import client
from tools import utils
from tools.base import lobby_shortcuts
from tools.base.matcher import MATCH_ANY
from tools.base import config


@utils.integration_test()
def test_raise_rpc_hook():
    if not config.TEST_WITH_HOOK:
        return

    room_id = "game_test_raise_rpc_hook"
    client_a, client_b, client_c, client_d = '2a', '2b', '2c', '2d'

    ca = utils.open_session(
        client_a, lambda: lobby_shortcuts.create_room(client_a, room_id=room_id))
    ca.send_msg_with_expect_msgs({'cmd': 'conv',
                                   'op': 'start',
                                   'cid': room_id,
                                   'hookName': 'normal'},
                                  [{'cmd': 'conv',
                                    'op': 'started',
                                    'masterActorId': 1,
                                    "members": [{"pid": client_a, "actorId": 1, "attr": {}}]}])

    future = client.add_expect_msgs_for_all(
        [ca], [{'cmd': 'conv', 'op': 'members-joined',
                'member': {'pid': client_b, 'actorId': 2, 'attr': {}}}])
    cb = utils.open_session(
        client_b, lambda: lobby_shortcuts.join_room(client_b, room_id))
    cb.send_msg_with_expect_msgs({'cmd': 'conv',
                                   'op': 'add',
                                   'cid': room_id},
                                  [{'cmd': 'conv',
                                    'op': 'added'},
                                   {'cmd': 'events',
                                    'events': []}])
    future.get()

    future = client.add_expect_msgs_for_all(
        [ca, cb], [{'cmd': 'conv', 'op': 'members-joined',
                    'member': {'pid': client_c, 'actorId': 3, 'attr': {}}}])
    cc = utils.open_session(
        client_c, lambda: lobby_shortcuts.join_room(client_c, room_id))
    cc.send_msg_with_expect_msgs({'cmd': 'conv',
                                   'op': 'add',
                                   'cid': room_id},
                                  [{'cmd': 'conv',
                                    'op': 'added'},
                                   {'cmd': 'events',
                                    'events': []}])
    future.get()

    # send msg to master then hook will change it to others
    future = client.add_expect_msgs_for_all(
        [ca, cc], [{'cmd': 'direct'}])
    cb.send_msg({'cmd': 'direct', 'msg': 'cache on room=', 'receiverGroup': 2, 'cacheOption': 1})
    future.get()

    future = client.add_expect_msgs_for_all(
        [ca], [{'cmd': 'direct'}])
    cb.send_msg({'cmd': 'direct', 'msg': 'cache on actor=', 'receiverGroup': 2, 'cacheOption': 2})
    future.get()

    # send msg to others then hook will change it to master
    future = client.add_expect_msgs_for_all(
        [ca], [{'cmd': 'direct'}])
    cb.send_msg({'cmd': 'direct', 'msg': 'no cache to master', 'receiverGroup': 0, 'cacheOption': 2})
    future.get()

    # send with actor ids
    future = client.add_expect_msgs_for_all(
        [cb], [{'cmd': 'direct'}])
    ca.send_msg({'cmd': 'direct', 'msg': 'to actors', 'receiverGroup': 0, 'toActorIds': [1, 2, 3]})
    future.get()

    # add new client to check cached msg
    cd = utils.open_session(
        client_d, lambda: lobby_shortcuts.join_room(client_d, room_id))
    cd.send_msg_with_expect_msgs({'cmd': 'conv',
                                   'op': 'add',
                                   'cid': room_id},
                                  [{'cmd': 'conv',
                                    'op': 'added'},
                                   {'cmd': 'events',
                                    'events': [{"fromActorId": 2, "timestamp": MATCH_ANY,
                                                "msg": "cache on room=modified-by-hook"},
                                               {"msg": "cache on actor=modified-by-hook",
                                                "timestamp": MATCH_ANY, "fromActorId": 2}]}])




if __name__ == '__main__':
    test_raise_rpc_hook()
