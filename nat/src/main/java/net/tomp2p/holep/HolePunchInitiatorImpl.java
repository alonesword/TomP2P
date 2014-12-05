package net.tomp2p.holep;

import io.netty.channel.SimpleChannelInboundHandler;
import net.tomp2p.connection.ChannelCreator;
import net.tomp2p.connection.HolePunchInitiator;
import net.tomp2p.futures.FutureDone;
import net.tomp2p.futures.FutureResponse;
import net.tomp2p.message.Message;
import net.tomp2p.p2p.Peer;

public class HolePunchInitiatorImpl implements HolePunchInitiator {
	
	private Peer peer;
	
	public HolePunchInitiatorImpl(Peer peer) {
		this.peer = peer;
	}

	@Override
	public FutureDone<FutureResponse> handleHolePunch(ChannelCreator channelCreator, int idleUDPSeconds,
			FutureResponse futureResponse, boolean broadcast, Message originalMessage, SimpleChannelInboundHandler<Message> handler) {
		GenericHolePuncher holePuncher = new GenericHolePuncher(peer, HolePunchInitiator.NUMBER_OF_HOLES, idleUDPSeconds, originalMessage);
		return holePuncher.initiateHolePunch(handler, channelCreator, futureResponse);
	}
	
	

	
}