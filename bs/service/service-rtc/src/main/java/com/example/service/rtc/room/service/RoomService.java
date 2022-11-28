package com.example.service.rtc.room.service;

import com.example.service.rtc.ws.service.WebsocketService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wzklhk
 */
@Service
public class RoomService {

    private Map<String, Set<WebsocketService>> roomMap = new ConcurrentHashMap<>();

    /**
     * 加入到大厅
     */
    public void enterLobby(WebsocketService connection) {
        Set<WebsocketService> lobby = roomMap.get("lobby");
        if (lobby == null) {
            roomMap.put("lobby", new HashSet<>());
            lobby = roomMap.get("lobby");
            lobby.add(connection);
        } else {
            lobby.add(connection);
        }
    }

    /**
     * 离开大厅
     */
    public void leaveLobby(WebsocketService connection) {
        System.out.println(connection);
        Set<WebsocketService> lobby = roomMap.get("lobby");
        lobby.remove(connection);
    }

    /**
     * 加入指定的房间
     */
    public String enterRoom(String roomId, WebsocketService connection) {

        String operate;
        Set<WebsocketService> room = roomMap.get(roomId);
        if (room == null) {
            roomMap.put(roomId, new HashSet<>());
            room = roomMap.get(roomId);
            room.add(connection);
            operate = "created";
        } else {
            room.add(connection);
            operate = "joined";
        }
        //离开大厅
        leaveLobby(connection);
        return operate;
    }

    /**
     * 离开指定的房间
     */
    public void leaveRoom(String roomId, WebsocketService connection) {
        if (roomId != null) {
            Set<WebsocketService> room = roomMap.get(roomId);
            if (room != null) {
                room.remove(connection);
                if (room.size() == 0) {
                    roomMap.remove(roomId);
                }
            }
        }
    }

    /**
     * 通过房间Id查询房间
     */
    public Set<WebsocketService> queryRoomById(String roomId) {
        return roomMap.get(roomId);
    }

    public Set<String> queryAllRoomName() {
        return roomMap.keySet();
    }

    /**
     * 查询所有存在的房间
     */
    public Collection<Set<WebsocketService>> queryAllRoom() {
        return roomMap.values();
    }
}
