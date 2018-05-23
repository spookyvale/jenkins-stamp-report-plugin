package it.eng.stamp.view;

import java.io.IOException;

import hudson.model.AbstractBuild;
import it.eng.stamp.Messages;
import it.eng.stamp.STAMPReportBuildAction;
import it.eng.stamp.results.DescartesReport;
import it.eng.stamp.results.MethodResult;

public class DescartesReportResultDisplay extends AbstractMutationDisplay {

	private static AbstractBuild<?, ?> currentBuild = null;
	public DescartesReportResultDisplay(final STAMPReportBuildAction buildAction)
			throws IOException {
		super(null, Messages.DescartesReport_DisplayName(), buildAction);
	}

	@Override
	protected void tally() {
		addPreviousBuildReportToExistingReport();
		getDescartesReport().setBuildAction(getBuildAction());
		for (MethodResult mr : getDescartesReport().getMethods()) {
			String packageName = mr.getPakg();
			if (!getChildrenNames().contains(packageName)) {
				addChild(new PackageSummaryDisplay(this, packageName, getBuildAction()));
			}
		}
		compute();
	}

	private void addPreviousBuildReportToExistingReport() {
		    // Avoid parsing all builds.
		    if (DescartesReportResultDisplay.currentBuild == null) {
		    	DescartesReportResultDisplay.currentBuild = getBuild();
		    } else {
		      if (DescartesReportResultDisplay.currentBuild != getBuild()) {
		    	  DescartesReportResultDisplay.currentBuild = null;
		        return;
		      }
		    }

		    AbstractBuild<?, ?> previousBuild = getBuild().getPreviousBuild();
		    if (previousBuild == null) {
		      return;
		    }

		    STAMPReportBuildAction previousPerformanceAction = previousBuild.getActions(STAMPReportBuildAction.class)
					.get(0);
		    if (previousPerformanceAction == null) {
		      return;
		    }

		    AbstractMutationDisplay previousBuildActionResults = previousPerformanceAction.getBuildActionResultsDisplay();
		    if (previousBuildActionResults == null) {
		      return;
		    }

		    DescartesReport lastReport = previousBuildActionResults.getDescartesReport();
		    getDescartesReport().setLastBuildReport(lastReport);
		  }


}
