package baseball.view;

import baseball.domain.GameResult;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    private static final String INPUT_MESSAGE = "1 ~ 9 사이의 숫자 3개를 입력해주세요: ";
    private static final String WRONG_INPUT_MESSAGE = "잘못된 입력값입니다. 다시 입력해주세요 (1 ~ 9 사이의 숫자)";
    private static final String GAME_END_MESSAGE = "모든 숫자를 맞췄습니다. 게임을 종료합니다.";
    private static final String INPUT_RESTART_MESSAGE = "게임을 다시 시작하려면 1, 종료하려면 2를 입력해 주세요";
    private static final String DELIMITER = "";
    private static final char MIN_BASEBALL_NUMBER = '1';
    private static final char MAX_BASEBALL_NUMBER = '9';
    private static final char RESTART_NUMBER = '1';
    private static final char EXIT_NUMBER = '2';
    private static final int KEEP_PLAY_NUMBER = 0;
    private static final int VALID_BASEBALL_COUNT = 3;
    private static final int FIRST_INDEX = 0;
    private static final int VALID_GAME_STATE_COUNT = 1;

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<Integer> getBaseballNumbers() {
        System.out.print(INPUT_MESSAGE);
        String inputBaseballs = scanner.nextLine();
        while (!isValidInput(inputBaseballs)) {
            inputBaseballs = scanner.nextLine();
        }
        return Arrays.stream(inputBaseballs.split(DELIMITER))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private boolean isValidInput(String inputBaseballs) {
        try {
            validateInput(inputBaseballs);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(WRONG_INPUT_MESSAGE);
            return false;
        }
    }

    private void validateInput(String inputBaseballs) {
        if (inputBaseballs.length() != VALID_BASEBALL_COUNT) {
            throw new IllegalArgumentException();
        }
        boolean isDuplicated = inputBaseballs.chars()
                .filter(baseball -> MIN_BASEBALL_NUMBER <= baseball && baseball <= MAX_BASEBALL_NUMBER)
                .distinct()
                .count() != VALID_BASEBALL_COUNT;
        if (isDuplicated) {
            throw new IllegalArgumentException();
        }
    }

    public int getGameState(GameResult gameResult) {
        if (!gameResult.isGameEnds()) {
            return KEEP_PLAY_NUMBER;
        }
        System.out.println(GAME_END_MESSAGE);
        System.out.println(INPUT_RESTART_MESSAGE);
        String gameState = scanner.nextLine();
        while (isValidGameState(gameState)) {
            gameState = scanner.nextLine();
        }
        return Integer.parseInt(gameState);
    }

    private boolean isValidGameState(String gameState) {
        try {
            validateGameState(gameState);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(WRONG_INPUT_MESSAGE);
            return false;
        }
    }

    private void validateGameState(String gameState) {
        if (gameState.length() != VALID_GAME_STATE_COUNT) {
            throw new IllegalArgumentException();
        }
        char gameStateNumber = gameState.charAt(FIRST_INDEX);
        if (gameStateNumber != RESTART_NUMBER && gameStateNumber != EXIT_NUMBER) {
            throw new IllegalArgumentException();
        }
    }
}
