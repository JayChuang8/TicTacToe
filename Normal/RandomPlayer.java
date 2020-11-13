import java.io.IOException;

public class RandomPlayer extends Player{
	
	public RandomPlayer(String newName, char newLetter) {
		super(newName, newLetter);
	}
	
	protected void play() throws IOException {
		super.play();
	}

	@Override
	protected void makeMove() {
		RandomGenerator rg = new RandomGenerator();
		
		while(true) {
			int i = rg.discrete(0, 2);
			int j = rg.discrete(0, 2);
			if(getMark(i, j) == ' ') {
				addMark(i, j);
				break;
			}
		}
	}
}
