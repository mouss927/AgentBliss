package com.bliss.model;
import java.beans.MethodDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import javax.print.DocFlavor.STRING;
 
import java.util.Map.Entry;
 
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.CpuTimer;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.json.JSONException;
import org.json.JSONObject;

public class Traitement {
	
	
	 
	
	
	public static float ram ()
	{
		float ram=0;
		float totalRam=0;
		float freeRame=0;

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
                     method.setAccessible(true);
                     if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
                                  Object value;
                                  try {
                                                value = method.invoke(operatingSystemMXBean);
                                  } catch (Exception e) {
                                                value = e;
                                  }
                                  if (method.getName().equals("getTotalPhysicalMemorySize"))
                                                totalRam = Float.parseFloat(value.toString()) / 1024 / 1024 / 1024;
                                  else if (method.getName().equals("getFreePhysicalMemorySize"))
                                                freeRame = Float.parseFloat(value.toString()) / 1024 / 1024 / 1024;
                     }
        }
        ram=100 - ((freeRame / totalRam) * 100);
		
		return ram;
		
	}

	public  static float disque() {
		float disque=0;

        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        Map<String, Float> disk = new HashMap<>();
        for (File root : roots) {
                     try {
                                  disque =(100 - ((float) root.getFreeSpace() / (float) root.getTotalSpace()) * 100);
                     } catch (Exception e) {
                                  System.out.println("0");
                     }
        }
    	return disque;    
	}

	public static boolean error(float val,String name)
	{
		boolean statut = true;
		if(name.equals("ram"))
		{
			if(val>90)
			{
				statut=false;
			}
		}
		else if(name.equals("disque"))
		{
			if(val>70)
			{
				statut=false;
			}
			
		}
		return statut;
		
	}

	public static String getMacAddress() {
	    // ??? NIC ? MAC??????????
	    try {
	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
	                .getNetworkInterfaces();
	        while (networkInterfaces.hasMoreElements()) {
	            NetworkInterface nic = networkInterfaces.nextElement();
	            byte[] hardwareAddress = nic.getHardwareAddress();
	            StringBuilder buffer = new StringBuilder();
	            if (null != hardwareAddress) {
	                for (byte b : hardwareAddress) {
	                    buffer.append(String.format("%02X", b));
	                }
	                return buffer.toString();
	            }
	        }
	        // nic???????????????????
	        return Long.toHexString(System.currentTimeMillis());
	    } catch (SocketException e) {
	        // ??????????????????????
	        return Long.toHexString(System.currentTimeMillis());
	    }
	}
}
