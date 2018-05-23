/*
 * The MIT License
 *
 * Copyright (c) 2013, Cisco Systems, Inc., a California corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package it.eng.stamp.results;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.stapler.export.Exported;

import hudson.model.Run;
import it.eng.stamp.STAMPReportBuildAction;

/**
 * Represents all the Report of Descartes with METHOD output format.
 * 
 * @author Valentina Di Giacomo
 */
public class DescartesReport implements Serializable {

	private static final long serialVersionUID = 3499017799686036745L;

	private List<MethodResult> methods = new ArrayList<MethodResult>();

	private transient Map<String, Map<String, Map<String, MethodResult>>> indexed;

	private transient int mutationCount = 0;
	private transient int detectedCount = 0;
	@SuppressWarnings("unused")
	private transient int notDetectedCount = 0;

	private Analysis analysis;

	private transient STAMPReportBuildAction buildAction;

	private DescartesReport lastReport;

	public DescartesReport() {
	}

	/**
	 * 
	 * @return Analysis obect with info
	 */
	public Analysis getAnalysis() {
		return analysis;
	}

	/**
	 * 
	 * @return mutation results sorted by method
	 */
	@Exported
	public List<MethodResult> getMethods() {
		return methods;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public void setMethods(List<MethodResult> methods) {
		this.methods = methods;
	}

	@Exported
	public List<String> getMutators() {
		return this.analysis.getMutators();
	}

	@Exported
	public int getDuration() {
		return this.analysis.getTime();

	}

	public STAMPReportBuildAction getBuildAction() {
		return buildAction;
	}

	public void setBuildAction(STAMPReportBuildAction buildAction) {
		this.buildAction = buildAction;
	}

	public DescartesReport getLastBuildReport() {
		return lastReport;
	}

	public void setLastBuildReport(DescartesReport lastReport) {
		this.lastReport = lastReport;
	}

	public String getName() {
		return String.format("Descartes Metric Report for Build %d (%s) -Execution:  %d ms", getBuild().getNumber(),
				getBuild().getTimestampString2(), getAnalysis().getTime());
	}

	public Run<?, ?> getBuild() {
		return buildAction.getBuild();
	}

	public double getCoverageAverage() {
		return (double) detectedCount / (double) mutationCount;
	}

	public void doIndex() {
		if(indexed != null)
			return;
		indexed = new HashMap<>();
		for (MethodResult m : methods) {
			if (!indexed.containsKey(m.getPakg())) {
				indexed.put(m.getPakg(), new HashMap<>());
			}
			if (!indexed.get(m.getPakg()).containsKey(m.getClassName()))
				indexed.get(m.getPakg()).put(m.getClassName(), new HashMap<>());
			indexed.get(m.getPakg()).get(m.getClassName()).put(m.getName(), m);

			mutationCount += m.getMutations().size();
			detectedCount += m.getDetected().size();
			notDetectedCount += m.getNotDetected().size();
		}
	}

	public MethodResult getByFullName(String pkg, String className, String methodName) {
		try {
			return indexed.get(pkg).get(className).get(methodName);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();     
	    doIndex();
	  }
}