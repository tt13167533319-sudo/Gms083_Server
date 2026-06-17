package org.gms.net.server.coordinator.session;

import org.gms.net.server.Server;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class HostHwidCache {
    private final ConcurrentHashMap<String, HostHwid> hostHwidCache = new ConcurrentHashMap<>(); // Key: remoteHost

    // 清理所有过期条目
    void clearExpired() {
        SessionDAO.deleteExpiredHwidAccounts();

        Instant now = Instant.ofEpochMilli(Server.getInstance().getCurrentTime());
        List<String> remoteHostsToRemove = new ArrayList<>();
        for (Map.Entry<String, HostHwid> entry : hostHwidCache.entrySet()) {
            if (now.isAfter(entry.getValue().expiry())) {
                remoteHostsToRemove.add(entry.getKey());
            }
        }

        for (String remoteHost : remoteHostsToRemove) {
            hostHwidCache.remove(remoteHost);
        }
    }

    // 添加条目：按IP分组存储，key为设备唯一标识（IP-HWID）
    void addEntry(String remoteHost, Hwid hwid) {
        hostHwidCache.put(remoteHost, HostHwid.createWithDefaultExpiry(hwid));
    }

    // 获取指定remoteHost的条目
    HostHwid getEntry(String remoteHost) {
        return hostHwidCache.get(remoteHost);
    }

    // 移除指定remoteHost的条目并返回HWID
    Hwid removeEntryAndGetItsHwid(String remoteHost) {
        HostHwid hostHwid = hostHwidCache.remove(remoteHost);
        return hostHwid == null ? null : hostHwid.hwid();
    }

    // 获取指定remoteHost的HWID
    Hwid getEntryHwid(String remoteHost) {
        HostHwid hostHwid = hostHwidCache.get(remoteHost);
        return hostHwid == null ? null : hostHwid.hwid();
    }

}
