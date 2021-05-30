public enum Colours {

    green("\u001B[32m"),
    red("\u001B[31m"),
    orange("\u001B[33m"),
    blue("\u001B[34m"),
    magenta("\u001B[35m"),
    defaultt("\u001B[0m");

    public static void printColoured(String text, Colours colour) {
        System.out.println(colour.getColour() + text + Colours.defaultt.getColour());
    }

    public static void printColouredWithoutLn(String text, Colours colour) {
        System.out.print(colour.getColour() + text + Colours.defaultt.getColour());
    }

    private String colour;

    Colours(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

}

//Enum Colours dokładnie taki sam jak Pana dla przejrzystości wyniku