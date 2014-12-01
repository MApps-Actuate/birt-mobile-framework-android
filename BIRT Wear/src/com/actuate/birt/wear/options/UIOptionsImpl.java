package com.actuate.birt.wear.options;

public class UIOptionsImpl implements UIOptions {
	private boolean enableAdvancedSort		 = true;
	private boolean enableAggregation		 = true;
	private boolean enableCalculatedColumn	 = true;
	private boolean enableChartProperty		 = true;
	private boolean enableChartSubType		 = true;
	private boolean enableCollapseExpand	 = true;
	private boolean enableColumnEdit		 = true;
	private boolean enableColumnResize		 = true;
	private boolean enableContentMargin		 = true;
	private boolean enableDataAnalyzer		 = true;
	private boolean enableDataExtraction	 = true;
	private boolean enableEditReport		 = true;
	private boolean enableExportReport		 = true;
	private boolean enableFilter			 = true;
	private boolean enableFacebookComments	 = true;
	private boolean enableFlashGadgetType	 = true;
	private boolean enableFormat			 = true;
	private boolean enableGroupEdit			 = true;
	private boolean enableHideShowItems		 = true;
	private boolean enableHighlight			 = true;
	private boolean enableHoverHighlight	 = true;
	private boolean enableLaunchViewer		 = true;
	private boolean enableLinkToThisPage	 = true;
	private boolean enableMainMenu			 = true;
	private boolean enableMoveColumn		 = true;
	private boolean enablePageBreak			 = true;
	private boolean enablePageNavigation	 = true;
	private boolean enableParameterPage		 = true;
	private boolean enablePrint				 = true;
	private boolean enableReorderColumns	 = true;
	private boolean enableRowResize			 = true;
	private boolean enableSaveDesign		 = true;
	private boolean enableSaveDocument		 = true;
	private boolean enableShowToolTip		 = true;
	private boolean enableSort				 = true;
	private boolean enableSuppressDuplicate  = true;
	private boolean enableSwitchView 		 = true;
	private boolean enableTextEdit 			 = true;
	private boolean enableTOC 				 = true;
	private boolean enableToolBar 			 = true;
	private boolean enableToolbarContextMenu = true;
	private boolean enableToolbarHelp 		 = true;
	private boolean enableTopBottomNFilter	 = true;
	private boolean enableUndoRedo			 = true;
	
	@Override
	public String getUIOptions() {
		String uioptions = new String();
		
		uioptions = "options.enableAdvancedSort(" 		+ this.getAdvancedSort() 		+ ");\n" +
					"options.enableAggregation(" 		+ this.getAggregation() 		+ ");\n" +
					"options.enableCalculatedColumn(" 	+ this.getCalculatedColumns() 	+ ");\n" +
					"options.enableChartProperty(" 		+ this.getChartProperty() 		+ ");\n" +
					"options.enableChartSubType(" 		+ this.getChartSubType() 		+ ");\n" +
					"options.enableCollapseExpand(" 	+ this.getCollapseExpand() 		+ ");\n" +
					"options.enableColumnEdit(" 		+ this.getColumnEdit() 			+ ");\n" +
					"options.enableColumnResize(" 		+ this.getColumnResize() 		+ ");\n" +
					"options.enableContentMargin(" 		+ this.getContentMargin() 		+ ");\n" +
					"options.enableDataAnalyzer(" 		+ this.getDataAnalyzer() 		+ ");\n" +
					"options.enableDataExtraction(" 	+ this.getDataExtraction() 		+ ");\n" +
					"options.enableEditReport(" 		+ this.getEditReport() 			+ ");\n" +
					"options.enableExportReport(" 		+ this.getExportReport() 		+ ");\n" +
					"options.enableFilter(" 			+ this.getFilter() 				+ ");\n" +
					"options.enableFacebookComments(" 	+ this.getFacebookComments() 	+ ");\n" +
					"options.enableFlashGadgetType(" 	+ this.getFlashGadgetType() 	+ ");\n" +
					"options.enableFormat(" 			+ this.getFormat() 				+ ");\n" +
					"options.enableGroupEdit(" 			+ this.getGroupEdit() 			+ ");\n" +
					"options.enableHideShowItems(" 		+ this.getHideShowItems() 		+ ");\n" +
					"options.enableHighlight(" 			+ this.getHighlight() 			+ ");\n" +
					"options.enableHoverHighlight(" 	+ this.getHoverHighlight() 		+ ");\n" +
					"options.enableLaunchViewer(" 		+ this.getLaunchViewer() 		+ ");\n" +
					"options.enableLinkToThisPage(" 	+ this.getLinkToThisPage() 		+ ");\n" +
					"options.enableMainMenu(" 			+ this.getMainMenu() 			+ ");\n" +
					"options.enableMoveColumn(" 		+ this.getMoveColumn() 			+ ");\n" +
					"options.enablePageBreak(" 			+ this.getPageBreak() 			+ ");\n" +
					"options.enablePageNavigation(" 	+ this.getPageNavigation() 		+ ");\n" +
					"options.enableParameterPage(" 		+ this.getParameterPage() 		+ ");\n" +
					"options.enablePrint(" 				+ this.getPageBreak() 			+ ");\n" +
					"options.enableReorderColumns("		+ this.getReorderColumns() 		+ ");\n" +
					"options.enableRowResize(" 			+ this.getRowResize() 			+ ");\n" +
					"options.enableSaveDesign(" 		+ this.getSaveDesign() 			+ ");\n" +
					"options.enableSaveDocument(" 		+ this.getSaveDocument() 		+ ");\n" +
					"options.enableShowToolTip(" 		+ this.getShowToolTip()			+ ");\n" +
					"options.enableSort(" 				+ this.getSort() 				+ ");\n" +
					"options.enableSuppressDuplicate(" 	+ this.getSuppressDuplicate() 	+ ");\n" +
					"options.enableSwitchView(" 		+ this.getSwitchView() 			+ ");\n" +
					"options.enableTextEdit(" 			+ this.getTextEdit() 			+ ");\n" +
					"options.enableTOC(" 				+ this.getTOC() 				+ ");\n" +
					"options.enableToolBar(" 			+ this.getToolBar() 			+ ");\n" +
					"options.enableToolbarContextMenu(" + this.getToolbarContextMenu() 	+ ");\n" +
					"options.enableToolbarHelp("        + this.getToolbarHelp() 		+ ");\n" +
					"options.enableTopBottomNFilter("   + this.getTopBottomNFilter() 	+ ");\n" +
					"options.enableUndoRedo("           + this.getUndoRedo() 			+ ");\n";
		
		return uioptions;
	}
	
	@Override
	public void enableAdvancedSort(boolean enable) {
		this.enableAdvancedSort = enable;
	}

	@Override
	public void enableAggregation(boolean enable) {
		this.enableAggregation = enable;
	}

	@Override
	public void enableCalculatedColumn(boolean enable) {
		this.enableCalculatedColumn = enable;
	}

	@Override
	public void enableChartProperty(boolean enable) {
		this.enableChartProperty = enable;
	}

	@Override
	public void enableChartSubType(boolean enable) {
		this.enableChartSubType = enable;
	}

	@Override
	public void enableCollapseExpand(boolean enable) {
		this.enableCollapseExpand = enable;
	}

	@Override
	public void enableColumnEdit(boolean enable) {
		this.enableCollapseExpand = enable;
	}

	@Override
	public void enableColumnResize(boolean enable) {
		this.enableColumnResize = enable;
	}

	@Override
	public void enableContentMargin(boolean enable) {
		this.enableContentMargin = enable;
	}

	@Override
	public void enableDataAnalyzer(boolean enable) {
		this.enableDataAnalyzer = enable;
	}

	@Override
	public void enableDataExtraction(boolean enable) {
		this.enableDataExtraction = enable;
	}

	@Override
	public void enableEditReport(boolean enable) {
		this.enableEditReport = enable;
	}

	@Override
	public void enableExportReport(boolean enable) {
		this.enableExportReport = enable;
	}

	@Override
	public void enableFilter(boolean enable) {
		 this.enableFilter = enable;
	}

	@Override
	public void enableFacebookComments(boolean enable) {
		this.enableFacebookComments = enable;
	}

	@Override
	public void enableFlashGadgetType(boolean enable) {
		this.enableFlashGadgetType = enable;
	}

	@Override
	public void enableFormat(boolean enable) {
		this.enableFormat = enable;
	}

	@Override
	public void enableGroupEdit(boolean enable) {
		this.enableGroupEdit = enable;
	}

	@Override
	public void enableHideShowItems(boolean enable) {
		this.enableHideShowItems = enable;
	}

	@Override
	public void enableHighlight(boolean enable) {
		this.enableHighlight = enable;
	}

	@Override
	public void enableHoverHighlight(boolean enable) {
		this.enableHoverHighlight = enable;
	}

	@Override
	public void enableLaunchViewer(boolean enable) {
		this.enableLaunchViewer = enable;
	}

	@Override
	public void enableLinkToThisPage(boolean enable) {
		this.enableLinkToThisPage = enable;
	}

	@Override
	public void enableMainMenu(boolean enable) {
		this.enableMainMenu = enable;
	}

	@Override
	public void enableMoveColumn(boolean enable) {
		this.enableMoveColumn = enable;
	}

	@Override
	public void enablePageBreak(boolean enable) {
		 this.enablePageBreak = enable;
	}

	@Override
	public void enablePageNavigation(boolean enable) {
		this.enablePageBreak = enable;
	}

	@Override
	public void enableParameterPage(boolean enable) {
		this.enableParameterPage = enable;
	}

	@Override
	public void enablePrint(boolean enable) {
		this.enablePrint = enable;
	}

	@Override
	public void enableReorderColumns(boolean enable) {
		this.enableReorderColumns = enable;
	}

	@Override
	public void enableRowResize(boolean enable) {
		this.enableRowResize = enable;
	}

	@Override
	public void enableSaveDesign(boolean enable) {
		this.enableSaveDesign = enable;
	}

	@Override
	public void enableSaveDocument(boolean enable) {
		this.enableSaveDocument = enable;
	}

	@Override
	public void enableShowToolTip(boolean enable) {
		this.enableShowToolTip = enable;
	}

	@Override
	public void enableSort(boolean enable) {
		this.enableSort = enable;
	}

	@Override
	public void enableSuppressDuplicate(boolean enable) {
		this.enableSuppressDuplicate = enable;
	}

	@Override
	public void enableSwitchView(boolean enable) {
		this.enableSwitchView = enable;
	}

	@Override
	public void enableTextEdit(boolean enable) {
		this.enableTextEdit = enable;
	}

	@Override
	public void enableTOC(boolean enable) {
		this.enableTOC = enable;
	}

	@Override
	public void enableToolBar(boolean enable) {
		this.enableToolBar = enable;
	}

	@Override
	public void enableToolbarContextMenu(boolean enable) {
		this.enableToolbarContextMenu = enable;
	}

	@Override
	public void enableToolbarHelp(boolean enable) {
		this.enableToolbarHelp = enable;
	}

	@Override
	public void enableTopBottomNFilter(boolean enable) {
		this.enableTopBottomNFilter = enable;
	}

	@Override
	public void enableUndoRedo(boolean enable) {
		this.enableUndoRedo = enable;
	}

	@Override
	public boolean getAdvancedSort() {
		return this.enableAdvancedSort;
	}

	@Override
	public boolean getAggregation() {
		return this.enableAggregation;
	}

	@Override
	public boolean getCalculatedColumns() {
		return this.enableCalculatedColumn;
	}

	@Override
	public boolean getChartProperty() {
		return this.enableChartProperty;
	}

	@Override
	public boolean getChartSubType() {
		return this.enableChartSubType;
	}

	@Override
	public boolean getCollapseExpand() {
		return this.enableCollapseExpand;
	}

	@Override
	public boolean getColumnEdit() {
		return this.enableColumnEdit;
	}

	@Override
	public boolean getColumnResize() {
		return this.enableColumnResize;
	}

	@Override
	public boolean getContentMargin() {
		return this.enableContentMargin;
	}

	@Override
	public boolean getDataAnalyzer() {
		return this.enableDataAnalyzer;
	}

	@Override
	public boolean getDataExtraction() {
		return this.enableDataExtraction;
	}

	@Override
	public boolean getEditReport() {
		return this.enableEditReport;
	}

	@Override
	public boolean getExportReport() {
		return this.enableExportReport;
	}

	@Override
	public boolean getFilter() {
		return this.enableFilter;
	}

	@Override
	public boolean getFacebookComments() {
		return this.enableFacebookComments;
	}

	@Override
	public boolean getFlashGadgetType() {
		return this.enableFlashGadgetType;
	}

	@Override
	public boolean getFormat() {
		return this.enableFormat;
	}

	@Override
	public boolean getGroupEdit() {
		return this.enableGroupEdit;
	}

	@Override
	public boolean getHideShowItems() {
		return this.enableHideShowItems;
	}

	@Override
	public boolean getHighlight() {
		return this.enableHighlight;
	}

	@Override
	public boolean getHoverHighlight() {
		return this.enableHoverHighlight;
	}

	@Override
	public boolean getLaunchViewer() {
		return this.enableLaunchViewer;
	}

	@Override
	public boolean getLinkToThisPage() {
		return this.enableLinkToThisPage;
	}

	@Override
	public boolean getMainMenu() {
		return this.enableMainMenu;
	}

	@Override
	public boolean getMoveColumn() {
		return this.enableMoveColumn;
	}

	@Override
	public boolean getPageBreak() {
		return this.enablePageBreak;
	}

	@Override
	public boolean getPageNavigation() {
		return this.enablePageNavigation;
	}

	@Override
	public boolean getParameterPage() {
		return this.enableParameterPage;
	}

	@Override
	public boolean getPrint() {
		return this.enablePrint;
	}

	@Override
	public boolean getReorderColumns() {
		return this.enableReorderColumns;
	}

	@Override
	public boolean getRowResize() {
		return this.enableRowResize;
	}

	@Override
	public boolean getSaveDesign() {
		return this.enableSaveDesign;
	}

	@Override
	public boolean getSaveDocument() {
		return this.enableSaveDocument;
	}

	@Override
	public boolean getShowToolTip() {
		return this.enableShowToolTip;
	}

	@Override
	public boolean getSort() {
		return this.enableSort;
	}

	@Override
	public boolean getSuppressDuplicate() {
		return this.enableSuppressDuplicate;
	}

	@Override
	public boolean getSwitchView() {
		return this.enableSwitchView;
	}

	@Override
	public boolean getTextEdit() {
		return this.enableTextEdit;
	}

	@Override
	public boolean getTOC() {
		return this.enableTOC;
	}

	@Override
	public boolean getToolBar() {
		return this.enableToolBar;
	}

	@Override
	public boolean getToolbarContextMenu() {
		return this.enableToolbarContextMenu;
	}

	@Override
	public boolean getToolbarHelp() {
		return this.enableToolbarHelp;
	}

	@Override
	public boolean getTopBottomNFilter() {
		return this.enableTopBottomNFilter;
	}

	@Override
	public boolean getUndoRedo() {
		return this.enableUndoRedo;
	}
	
}
