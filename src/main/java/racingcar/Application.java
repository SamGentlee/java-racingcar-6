package racingcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {
    public static void main(String[] args) {
    	startGameProcess();
    }
    
	//게임 시작전 안내문 출력
    public static void startGame() {
    	System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
    }
    
    //차 이름(쉼표로 구분된 여러 이름) 입력받기
    public static String inputCarName(){
    	String carNames = Console.readLine();
    	return carNames;
    }
    
    //차 이름 유효성 검사
    public static void checkCarNameValidation(String carName) {
    	Validator.validateCarName(carName);
    }
    
    //입력받은 차 이름 분리하기(쉼표로 구분하기)
    public static List<String> separateCarNameInput(String carNames){
    	List<String> carList = new ArrayList<>();
    	String[] carNamesArr = carNames.split(",");
    	for(int i=0;i<carNamesArr.length;i++) {
    		checkCarNameValidation(carNamesArr[i]);
    		carList.add(carNamesArr[i]);
    	}
    	return carList;
    }
    
    //차 이동횟수 유효성 검사
    public static void checkNumberValidation(String movingNumberStr) {
    	Validator.valdateMovingNumber(movingNumberStr);
    }
    
    //차 이동횟수 입력받기
    public static int inputMovingNumber() {
    	System.out.println("시도할 횟수 몇회인가요?");
    	String movingNumberStr = Console.readLine();
    	checkNumberValidation(movingNumberStr);
    	int movingNumber = Integer.parseInt(movingNumberStr);
    	return movingNumber;
    }
    
    //차 이름을 HashMap<차이름,전진횟수>에 넣기
    public static Map<String, Integer> setCarRacingRecord(List<String> carList){
    	Map<String,Integer> carRacingRecord = new HashMap<String,Integer>();
    	for(String carName : carList) {
    		carRacingRecord.put(carName, 0);
    	}
    	return carRacingRecord;
    }
    
    //랜덤한 수 출력하기
    public static int getRandomNumber() {
    	int randomNumber = Randoms.pickNumberInRange(0,9);
    	return randomNumber;
    }
    
    //랜덤 수가 4이상인지 판별하기
    public static boolean checkRandomNumber(int randomNumber) {
    	if(randomNumber >= 4) {
    		return true;
    	}
    	return false;
    }
    
    //랜덤수를 통해 전진조건이 된 차 전진하기
    public static Map<String, Integer> updateCarRacingRecord(List<String> carList, Map<String, Integer> carRacingRecord){
    	for(String carName : carList) {
			int randomNumber = getRandomNumber();
			if(checkRandomNumber(randomNumber)) {
				carRacingRecord.put(carName, carRacingRecord.get(carName)+1);
			}
		}
    	return carRacingRecord;
    }
    
    //전진 횟수 (-)으로 표시하기
    public static String getRacingRecord(int recordNumber) {
    	StringBuilder racingRecord = new StringBuilder();
    	for(int i=0;i<recordNumber;i++) {
    		racingRecord.append("-");
    	}
    	return racingRecord.toString();
    }
    
    //전진이 된 차 기록 출력하기 
    public static void printCarRacingRecord(List<String> carList, Map<String,Integer> carRacingRecord) {
    	for(String carName : carList) {
    		String racingRecord=getRacingRecord(carRacingRecord.get(carName));
    		System.out.println(carName+" : "+racingRecord);
    	}
    }
    
    //횟수별 실행결과 출력하기
    public static void printCarRacingResult(int movingNumber, List<String> carList, Map<String, Integer> carRacingRecord) {
    	System.out.println("\n실행 결과");
		for(int i=0;i < movingNumber;i++) {
			updateCarRacingRecord(carList,carRacingRecord);
			printCarRacingRecord(carList, carRacingRecord);
			System.out.println();
		}
    }
    
    //저장된 Map에서 최댓값을 가지는 차 이름 가져오기
    public static List<String> getWinner(Map<String, Integer> carRacingRecord, int maxRecord){
    	List<String> winners = new ArrayList<>();
    	for(String carName : carRacingRecord.keySet()) {
    		if(carRacingRecord.get(carName)==maxRecord) {
    			winners.add(carName);
    		}
    	}
    	return winners;
    }
    
    //결과 출력
    public static void printGameResult(List<String> carList, Map<String, Integer> carRacingRecord) {
    	int maxRecord = 0;
    	for(String carName : carList) {
    		String racingRecord=getRacingRecord(carRacingRecord.get(carName));
    		maxRecord = Math.max(maxRecord,racingRecord.length());
    	}
    	List<String> winners = getWinner(carRacingRecord,maxRecord);
    	StringBuilder winnerName= new StringBuilder();
    	for(int i=0;i<winners.size()-1;i++) {
    		winnerName.append(winners.get(i)+", ");
    	}
    	winnerName.append(winners.get(winners.size()-1));
    	System.out.println("최종 우승자 : "+winnerName.toString());
    }
    
    //전체 게임 과정
	private static void startGameProcess() {
		startGame();
		String carNames = inputCarName();
		List<String> carList=separateCarNameInput(carNames);
		int movingNumber=inputMovingNumber();
		Map<String, Integer> carRacingRecord = setCarRacingRecord(carList);
		printCarRacingResult(movingNumber,carList,carRacingRecord);
		printGameResult(carList,carRacingRecord);
	}
    
}
