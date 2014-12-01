Under Construction: birt-mobile-framework-android
=============================

This is the Java-based framework to be used by Android mobile app developers to include BIRT content into their apps.

How to
=============================
When creating a BIRTWear object you can do one of two things.  You can either pass all required parameters in the constructor
```
UIOptions uiOptions = new UIOptionsImpl();
BIRTWear  birtWear  = new BIRTWearImpl("Administrator",                                          // Username
                                       "",                                                       // Password
                                       "Default Volume",                                         // Volume Name
									   "http://localhost:8700/iportal/jsapi",                    // JSAPI Location
									   "http://localhost:8700/iportal",                          // iPortal Location
									   "reqOps.setCustomParameters({'__masterpage': 'false'});", // Custom parameters (can be null)
									   "Report Designs/Account Overview Table.rptdesign",        // Report name (full path)
									   uiOptions);                                               // UI Options (can be null)
String jsapi = birtWear.getReport();  // Processed JSAPI from BIRTWear, this can be used wherever you need a BIRT Report
```

Or you can use the setters to create the same object

```
BIRTWear  birtWear  = new BIRTWearImpl();
UIOptions uiOptions = new UIOptionsImpl();
birtWear.setJSAPILocation("http://localhost:8700/jsapi");
birtWear.setVolume("Default Volume");
birtWear.setUsername("Administrator");
birtWear.setPassword("");
birtWear.setReportName("Report Designs/test.rptdesign");
birtWear.setUIOptions(uiOptions);
birtWear.setIportalLocation("http://localhost:8700/iportal");
birtWear.setCustomParameters("reqOps.setCustomParameters({'__masterpage': 'false'});");
String jsapi = birtWear.getReport();
// PLACE jsapi IN YOUR WEBVIEW
```

Once you have the JSAPI in a String you can use it wherever you need your BIRT report.  For Android application you can use Webview.loadDataWithBaseUrl()


Sample output
=============================
```
<html>

<head>
    <meta name='viewport' content='initial-scale=1'>
</head>

<body style='margin:0; padding:0'>
    <script type='text/javascript' language='JavaScript' src='http://localhost:8700/iportal/jsapi'></script>
    <script type='text/javascript'>
        actuate.load('viewer');
        var reqOps = new actuate.RequestOptions();
        reqOps.setVolume('Default Volume');
        reqOps.setCustomParameters({
            '__masterpage': 'false'
        });
        actuate.initialize('http://localhost:8700/iportal', reqOps == undefined ? null : reqOps, 'Administrator', '', myInit);

        function myInit() {
            viewer1 = new actuate.Viewer('container1');
            viewer1.setReportName('Report Designs/Account Overview Table.rptdesign');
            viewer1.setContentMargin(0);
            var options = new actuate.viewer.UIOptions();
            options.enableAdvancedSort(true);
            options.enableAggregation(true);
            options.enableCalculatedColumn(true);
            options.enableChartProperty(true);
            options.enableChartSubType(true);
            options.enableCollapseExpand(true);
            options.enableColumnEdit(true);
            options.enableColumnResize(true);
            options.enableContentMargin(true);
            options.enableDataAnalyzer(true);
            options.enableDataExtraction(true);
            options.enableEditReport(true);
            options.enableExportReport(true);
            options.enableFilter(true);
            options.enableFacebookComments(true);
            options.enableFlashGadgetType(true);
            options.enableFormat(true);
            options.enableGroupEdit(true);
            options.enableHideShowItems(true);
            options.enableHighlight(true);
            options.enableHoverHighlight(true);
            options.enableLaunchViewer(true);
            options.enableLinkToThisPage(true);
            options.enableMainMenu(true);
            options.enableMoveColumn(true);
            options.enablePageBreak(true);
            options.enablePageNavigation(true);
            options.enableParameterPage(true);
            options.enablePrint(true);
            options.enableReorderColumns(true);
            options.enableRowResize(true);
            options.enableSaveDesign(true);
            options.enableSaveDocument(true);
            options.enableShowToolTip(true);
            options.enableSort(true);
            options.enableSuppressDuplicate(true);
            options.enableSwitchView(true);
            options.enableTextEdit(true);
            options.enableTOC(true);
            options.enableToolBar(true);
            options.enableToolbarContextMenu(true);
            options.enableToolbarHelp(true);
            options.enableTopBottomNFilter(true);
            options.enableUndoRedo(true);

            viewer1.setUIOptions(options);
            viewer1.submit();
        }
    </script>
    <div id='container1' style='border-width: 0px; border-style: solid;'></div>
</body>

</html>
```
