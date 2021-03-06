package cn.shiyanjun.platform.network.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.shiyanjun.platform.api.Context;
import cn.shiyanjun.platform.network.api.MessageDispatcher;

public final class Inbox extends MessageBox<InboxMessage> {

	private static final Log LOG = LogFactory.getLog(Inbox.class);
	
	public Inbox(Context context, MessageDispatcher dispatcher) {
		super(context, dispatcher);
		name = "INBOX";
	}
	
	@Override
	public void start() {
		super.start();
		super.getExecutorService().execute(this);
	}
	
	@Override
	public void stop() {
		super.stop();
	}
	
	@Override
	public void run() {
		while(true) {
			InboxMessage message = null;
			try {
				message = messageBox.take();
				if(message != null) {
					dispatcher.dispatch(message);
					LOG.debug("Message dispatched: dispatcher=" + dispatcher.getClass().getName() + ", message=" + message);
				}
			} catch (Exception e) {
				LOG.warn("Fail to dispatch message: " + message, e);
			}
		}
	}
}
