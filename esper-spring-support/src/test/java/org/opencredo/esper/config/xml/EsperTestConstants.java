package org.opencredo.esper.config.xml;

public class EsperTestConstants {
	public static final String EPL = "select count(*) as throughput from java.lang.Object.win:time_batch(1 second)";
	
	public static final String LISTENER_TEST_CLASS = "org.opencredo.esper.CallRecordingListener";

	public static final String DEFAULT_TEMPLATE_NAME = "testTemplate";
}
