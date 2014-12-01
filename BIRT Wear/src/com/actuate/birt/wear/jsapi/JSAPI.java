package com.actuate.birt.wear.jsapi;

public class JSAPI {
	private final String jsapi;
	
	public JSAPI() {
		jsapi = "<html>\n" +
				"<head><meta name='viewport' content='initial-scale=1'></head>\n" +
				"<body style='margin:0; padding:0'>\n" +
				"<script type='text/javascript' language='JavaScript' src='JSAPILOCATION'></script>\n" +
				"<script type='text/javascript'>\n" +
				"actuate.load('viewer');\n" +
				"var reqOps = new actuate.RequestOptions();\n" +
				"reqOps.setVolume('VOLUME');\n" +
				"CUSTOMPARAMETERS\n" +
				"actuate.initialize('IPORTALLOCATION', reqOps == undefined ? null : reqOps, 'USERNAME', 'PASSWORD', myInit);\n" +
				"\n" +
				"function myInit() {\n" +
				"   viewer1 = new actuate.Viewer('container1');\n" +
				"   viewer1.setReportName('REPORTNAME');\n" +
				"   viewer1.setContentMargin(0);\n" +
				"   var options = new actuate.viewer.UIOptions();\n" +
				"   SETOPTIONS\n" +
				"   viewer1.setUIOptions(options);\n" +
				"   viewer1.submit();\n" +
				"}\n" +
				"</script>\n" +
				"<div id='container1' style='border-width: 0px; border-style: solid;'></div>\n" +
				"</body>\n" +
				"</html>\n";
	}
	
	public String getJSAPI() {
		return this.jsapi;
	}	
}
