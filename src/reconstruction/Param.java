package reconstruction;

public class Param {
	public enum Region {Asia, US, Europe};	
	public enum SubsRatioMode {None, ModifyRatio};	
	public enum InterestGeneralizationMode {None, Proportional, Uniform};
	public enum KernelFunc {uniform, triangular, epanechnikov, tricube, gaussian}
}