package logic.model.player;

import java.util.Random;


public class ComputerPlayer extends Player
{
    public ComputerPlayer()
    {
        super(new NameGenerator().randomName());
    }

    private static class NameGenerator
    {
        public static final int MAX_RANDOM_NAME_LENGTH = 8;
        public static final int MIN_RANDOM_NAME_LENGTH = 3;
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
        final Random rand    = new Random();

        public String randomName()
        {
            String generatedName = "";
            int randomNameLength = getRandomNameLength();
            for (int i = 0; i < randomNameLength; i++)
            {
                generatedName += getRandomChar();
            }
            return generatedName;
        }

        private int getRandomNameLength()
        {
            return rand.nextInt(MAX_RANDOM_NAME_LENGTH - MIN_RANDOM_NAME_LENGTH) + MIN_RANDOM_NAME_LENGTH;
        }

        private char getRandomChar()
        {
            return lexicon.charAt(rand.nextInt(lexicon.length()));
        }
    }
}
