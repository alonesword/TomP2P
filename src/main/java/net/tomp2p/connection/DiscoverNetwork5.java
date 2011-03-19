package net.tomp2p.connection;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import net.tomp2p.connection.Bindings.Protocol;

public class DiscoverNetwork5 implements DiscoverNetwork
{
	private Bindings bindings;

	@Override
	public void init(Bindings bindings)
	{
		this.bindings = bindings;
	}

	@Override
	public StringBuilder discoverNetwork(NetworkInterface networkInterface)
	{
		StringBuilder sb = new StringBuilder();
		// works in 1.5
		for (Enumeration<InetAddress> e = networkInterface.getInetAddresses(); e.hasMoreElements();)
		{
			// works in 1.5
			InetAddress inet = e.nextElement();
			if (bindings.getAddresses().contains(inet)) 
				continue;
			if (bindings.useAllProtocols())
			{
				sb.append(",All:").append(inet);
				bindings.addAddress(inet);
			}
			else
			{
				if (inet instanceof Inet4Address && bindings.getProtocols().contains(Protocol.IPv4))
				{
					sb.append(",IPv4:").append(inet);
					bindings.addAddress(inet);
				}
				if (inet instanceof Inet6Address && bindings.getProtocols().contains(Protocol.IPv6))
				{
					sb.append(",IPv6:").append(inet);
					bindings.addAddress(inet);
				}
			}
		}
		return sb.replace(0, 1, "(").append(")");
	}
}
