package com.hoolai.chatmonitor.common.returnvalue;

import com.google.common.base.Strings;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultIdGenerator implements IdGenerator {

    private IdGenerator idGenerator = new IdGenerator() {
        private AtomicLong id = new AtomicLong(0);

        @Override
        public String generate() {
            return id.incrementAndGet() + "";
        }
    };

    public void setIdGenerator(IdGenerator idGenerator) {
        if (idGenerator != null) {
            this.idGenerator = idGenerator;
        }
    }

    @Override
    public String generate() {
        String cache = getCache(true);
        String[] split = cache.split("\\.");
        return idGenerator.generate() + "_" + split[split.length - 1];
    }

    private static String internalIpCache;
    private static String externalIpCache;

    private static String getCache(boolean internal) {
        if (internal) {
            if (Strings.isNullOrEmpty(internalIpCache)) {
                synchronized (DefaultIdGenerator.class) {
                    if (Strings.isNullOrEmpty(internalIpCache)) {
                        internalIpCache = get(internal);
                    }
                }
            }
            return internalIpCache;
        } else {
            if (Strings.isNullOrEmpty(externalIpCache)) {
                synchronized (DefaultIdGenerator.class) {
                    if (Strings.isNullOrEmpty(internalIpCache)) {
                        externalIpCache = get(internal);
                    }
                }
            }
            return externalIpCache;
        }
    }

    public static String get(boolean internal) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        String ip = inetAddress.getHostAddress();
                        if (isInnerIP(ip) == internal) {
                            return ip;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp = false;
        long ipNum = getIpNum(ipAddress);
        /**
         私有IP：A类  10.0.0.0-10.255.255.255
         B类  172.16.0.0-172.31.255.255
         C类  192.168.0.0-192.168.255.255
         当然，还有127这个网段是环回地址
         **/
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd);
        return isInnerIp;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    public static void main(String[] args) {
        System.out.println(get(true));
        System.out.println(get(false));
    }

}
