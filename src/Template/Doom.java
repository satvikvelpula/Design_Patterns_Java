package Template;

public class Doom extends Game {

    private int monsterHealth;
    private int[] playerHealth;
    private int winner;

    @Override
    public void initializeGame(int numberOfPlayers) {

        monsterHealth = 6;
        playerHealth = new int[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            playerHealth[i] = 3;
        }

        winner = -1;

        System.out.println("The game has started!");
        System.out.println("Players: " + numberOfPlayers);
        System.out.println("Monster health: " + monsterHealth);
    }

    @Override
    public boolean endOfGame() {

        return monsterHealth <= 0;
    }

    @Override
    public void playSingleTurn(int player) {

        System.out.println("Player " + (player + 1) + "'s turn.");

        monsterHealth--;

        System.out.println(
                "Player " + (player + 1) +
                " attacked the monster!"
        );

        System.out.println(
                "Monster health: " + monsterHealth
        );

        if (monsterHealth <= 0) {
            winner = player;
        }
    }

    @Override
    public void displayWinner() {

        System.out.println(
                "Player " + (winner + 1) + " wins!"
        );
    }
}