
public class SmartPlayer extends BlockingPlayer{

	public SmartPlayer(String name, char letter) {
		super(name, letter);
	}

	@Override
	protected void makeMove() {
		boolean test = false;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++)
				if(super.getMark(i, j) == ' ') {
					test = testForBlocking(i, j, mark);
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
	
	protected boolean  testForWinning(int row, int col, char m) {
		return testForBlocking(row, col, m);
	}
}
