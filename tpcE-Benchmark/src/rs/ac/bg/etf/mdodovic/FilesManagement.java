package rs.ac.bg.etf.mdodovic;

import java.util.ArrayList;
import java.util.List;

public class FilesManagement {

//	public static final String pathToTransactionMixFolder = "D:/transactionMix/3e6_scale/";
	public static final String pathToTransactionMixFolder = "C:/Users/matij/Desktop/transactionMix/3e6_scale/";

	public static final String pathToResultFolderNormalized = "C:/Users/matij/Desktop/tpcE-Benchmark-MySQL/results/normalized/";

	public static final String pathToResultFolderFullyDenormalized = "C:/Users/matij/Desktop/tpcE-Benchmark-MySQL/results/fully_denormalized/";
	public static final String pathToResultFolderPartiallyDenormalized = "C:/Users/matij/Desktop/tpcE-Benchmark-MySQL/results/partially_denormalized/";

//	public static final String pathToData = "D:/flatOut_dataForDatabase/3e6_scale/";
	public static final String pathToData = "C:/Users/matij/Desktop/flatOut_dataForDatabase/3e6_scale/";

	
	public static List<String> transactionMixFilesList = new ArrayList<String>();
	public static List<String> outputResultFileNameList = new ArrayList<String>();

	static {
		transactionMixFilesList.add(FilesManagement.pathToTransactionMixFolder + "T2T3T8_T2F1_read_130k.sql");
		transactionMixFilesList.add(FilesManagement.pathToTransactionMixFolder + "T2T3T8_T3F1_write_130k.sql");
		transactionMixFilesList.add(FilesManagement.pathToTransactionMixFolder + "T2T3T8_T8F2_write_130k.sql");
		transactionMixFilesList.add(FilesManagement.pathToTransactionMixFolder + "T2T3T8_T8F6_write_130k.sql");
//		transactionMixFilesList.add(FilesManagement.pathToTransactionMixFolder + "T2T3T8_T3T8_all_write_130k.sql");

		outputResultFileNameList.add("T2F1_read_130k");
		outputResultFileNameList.add("T3F1_write_130k");
		outputResultFileNameList.add("T8F2_write_130k");
		outputResultFileNameList.add("T8F6_write_130k");
//		outputResultFileNameList.add("T3T8_all_write_130k");

	}

	
}
