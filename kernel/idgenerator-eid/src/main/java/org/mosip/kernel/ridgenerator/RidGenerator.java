package org.mosip.kernel.ridgenerator;

import java.util.Random;

import org.mosip.kernel.core.spi.idgenerator.MosipRidGenerator;
import org.mosip.kernel.core.utils.StringUtil;
import org.mosip.kernel.ridgenerator.constants.RidGeneratorConstants;
import org.mosip.kernel.ridgenerator.exception.MosipEmptyInputException;
import org.mosip.kernel.ridgenerator.exception.MosipInputLengthException;
import org.mosip.kernel.ridgenerator.exception.MosipNullValueException;

/**
 * This class generates Registration ID as per defined policy
 * 
 * @author Sidhant Agarwal
 * @since 1.0.0
 *
 */
public class RidGenerator implements MosipRidGenerator {

	public static final Random randomNumberGenerator = new Random();
	protected String agentRid;
	protected String machineRid;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mosip.kernel.core.spi.idgenerator.MosipEidGenerator#eidGeneration(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public String ridGeneration(String agentId, String machineId) {
		String rid = null;
		checkInput(agentId, machineId);
		cleanupId(agentId, machineId);
		int randomDigitEid = randomNumberGen();
		long currentTimestamp = getCurrTimestamp();
		rid = appendString(randomDigitEid, currentTimestamp);
		return rid;
	}

	/**
	 * This method is used to validate the input given by user
	 * 
	 * @param agentId
	 *            input by user
	 * @param machineId
	 *            input by user
	 */
	public void checkInput(String agentId, String machineId) {

		if (agentId == null || machineId == null) {

			throw new MosipNullValueException(RidGeneratorConstants.MOSIP_NULL_VALUE_ERROR_CODE.getErrorCode(),
					RidGeneratorConstants.MOSIP_NULL_VALUE_ERROR_CODE.getErrorMessage());
		}
		if (agentId.isEmpty() || machineId.isEmpty()) {

			throw new MosipEmptyInputException(RidGeneratorConstants.MOSIP_EMPTY_INPUT_ERROR_CODE.getErrorCode(),
					RidGeneratorConstants.MOSIP_EMPTY_INPUT_ERROR_CODE.getErrorMessage());
		}
		if (agentId.length() < 4 || machineId.length() < 5) {

			throw new MosipInputLengthException(RidGeneratorConstants.MOSIP_INPUT_LENGTH_ERROR_CODE.getErrorCode(),
					RidGeneratorConstants.MOSIP_INPUT_LENGTH_ERROR_CODE.getErrorMessage());
		}

	}

	/**
	 * This method is used to clean up the input id
	 * 
	 * @param agentId
	 *            input by user
	 * @param machineId
	 *            input by user
	 */
	public void cleanupId(String agentId, String machineId) {
		agentRid = StringUtil.removeLeftChar(agentId, 4);
		machineRid = StringUtil.removeLeftChar(machineId, 5);

	}

	/**
	 * This method generates a five digit random number
	 * 
	 * @return generated five digit random number
	 */
	public int randomNumberGen() {
		return (10000 + randomNumberGenerator.nextInt(90000));

	}

	/**
	 * This method gets the current timestamp in milliseconds
	 * 
	 * @return current timestamp in thirteen digits
	 */
	public long getCurrTimestamp() {
		return (System.currentTimeMillis());
	}

	/**
	 * This method appends the different strings to generate the RID
	 * 
	 * @param randomDigitRid
	 *            5 digit no. generated
	 * @param currentTimestamp
	 *            current timestamp generated
	 * @return generated RID
	 */
	public String appendString(int randomDigitRid, long currentTimestamp) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(agentRid).append(machineRid).append(randomDigitRid).append(currentTimestamp);
		return (stringBuilder.toString().trim());
	}
}
