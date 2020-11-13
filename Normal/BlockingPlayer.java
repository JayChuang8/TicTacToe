import java.io.IOException;

public class BlockingPlayer extends RandomPlayer{

	public BlockingPlayer(String name, char letter) {
		super(name, letter);
	}

	@Override
	protected void makeMove() {
		boolean test = false;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++)
				if(super.getMark(i, j) == ' ') {
					test = testForBlocking(i, j, opponent.mark);
					if(test == true) {
						super.addMark(i, j);
						i = j = 3;
						break;
					}
				}
		}
		if(test == false)
			super.makeMove();
	}
	
	protected boolean testForBlocking(int row, int col, char mark) {
		
		if(super.opponent.mark == mark)
			super.opponent.addMark(row, col);
		else
			super.addMark(row, col);
		
		if(super.board.checkWinner(mark) == 1) {
			return true;
		}
		
		super.board.addMark(row, col, ' ');
		return false;
	}
}
