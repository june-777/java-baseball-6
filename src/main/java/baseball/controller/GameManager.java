package baseball.controller;

import baseball.service.BaseballCollection;
import baseball.service.Judgement;
import baseball.service.RandomNumberGenerator;
import baseball.view.ConsoleInput;
import baseball.view.Input;
import baseball.view.Output;

public class GameManager {
    // TODO: 객체 직접 생성 제거하도록 리팩터링 해야함
    // TODO: 컴퓨터 공과 사용자 공은 핵심 비즈니스이다. 이 정보를 굳이 컨트롤러에 노출하는게 맞을까?
    private RestartCommandValidator validator = new RestartCommandValidator();
    private Judgement judgment = new Judgement();
    private Input input = new ConsoleInput();

    // 기능: 게임을 최초로 시작한다
    public void initialGameStart() {
        Output.printInitialGameStartMessage();
        while (true) {
            progressGame();
            if (isNoMoreGame()) {
                break;
            }
        }
    }

    // 기능: 게임 순서에 맞게 게임을 진행한다
    private void progressGame() {
        BaseballCollection computerBalls = new BaseballCollection(new RandomNumberGenerator());
        while (true) {
            Output.printNumberInputMessage();
            BaseballCollection playerBalls = new BaseballCollection(input.readLine());
            String hint = judgment.calculateHint(computerBalls, playerBalls);
            Output.printHint(hint);
            if (isGameEnd(hint)) {
                break;
            }
        }
    }

    // 기능: 힌트가 3스트라이크면 게임 종료
    private boolean isGameEnd(String hint) {
        if (hint.equals("3스트라이크")) {
            Output.printGameEndMessage();
            return true;
        }
        return false;
    }

    // 기능: 게임 종료 후, 게임 재시작 여부
    private boolean isNoMoreGame() {
        Output.printRestartGameMessage();
        String restartCommand = input.readLine();
        validator.validateRestartCommandInput(restartCommand);
        if (restartCommand.equals("2")) {
            return true;
        }
        return false;
    }
}
