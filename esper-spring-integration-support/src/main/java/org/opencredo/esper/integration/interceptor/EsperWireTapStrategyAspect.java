package org.opencredo.esper.integration.interceptor;

//@Aspect
public class EsperWireTapStrategyAspect {
	
	private boolean preSend;
	
	private boolean postSend;
	
	private boolean preReceive;
	
	private boolean postReceive;
	
	public boolean isPreSend() {
		return preSend;
	}

	public void setPreSend(boolean preSend) {
		this.preSend = preSend;
	}

	public boolean isPostSend() {
		return postSend;
	}

	public void setPostSend(boolean postSend) {
		this.postSend = postSend;
	}

	public boolean isPreReceive() {
		return preReceive;
	}

	public void setPreReceive(boolean preReceive) {
		this.preReceive = preReceive;
	}

	public boolean isPostReceive() {
		return postReceive;
	}

	public void setPostReceive(boolean postReceive) {
		this.postReceive = postReceive;
	}

//	@Around
	public void enforceStrategy(/*ProceedingJoinPoint pjp*/) {
		// TODO Enforce strategy for when esper should be notified for
		// each of the different types of events.
	}
}
