package com.magicvault.card;

// Importing the JsonProperty annotation from Jackson for JSON to Java object mapping.
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Defining the public class ScryfallCard, representing a Magic: The Gathering card with data from Scryfall.
public class ScryfallCard {

    // Declaring private attributes of the class.
    private String name;          // The name of the card.
    private String typeLine;      // The type line of the card, including types and subtypes.
    private String oracleText;    // The oracle text of the card, which includes rules text.
    private ImageUris imageUris;  // URLs of the card's images.
    private String manaCost;      // The mana cost of the card.
    private String power;         // The power of the card (if it's a creature).
    private String toughness;     // The toughness of the card (if it's a creature).
    private List<String> colors;  // The colors of the card.
    private List<String> colorIdentity; // The color identity of the card.
    private Prices prices;        // The prices of the card.

    // Getter method for the card name, annotated with JsonProperty for JSON mapping.
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    // Setter method for the card name.
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for the type line of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("type_line")
    public String getTypeLine() {
        return typeLine;
    }

    // Setter method for the type line of the card.
    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    // Getter method for the oracle text of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("oracle_text")
    public String getOracleText() {
        return oracleText;
    }

    // Setter method for the oracle text of the card.
    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    // Getter method for the image URLs of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("image_uris")
    public ImageUris getImageUris() {
        return imageUris;
    }

    // Setter method for the image URLs of the card.
    public void setImageUris(ImageUris imageUris) {
        this.imageUris = imageUris;
    }

    // Inner class to represent image URLs of the card.
    public static class ImageUris {
        private String png; // URL for the PNG image of the card.

        // Getter method for the PNG image URL, annotated with JsonProperty for JSON mapping.
        @JsonProperty("png")
        public String getPng() {
            return png;
        }

        // Setter method for the PNG image URL.
        public void setPng(String png) {
            this.png = png;
        }
    }

    // Getter method for the mana cost of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("mana_cost")
    public String getManaCost() {
        return manaCost;
    }

    // Setter method for the mana cost of the card.
    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    // Getter method for the power of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("power")
    public String getPower() {
        return power;
    }

    // Setter method for the power of the card.
    public void setPower(String power) {
        this.power = power;
    }

    // Getter method for the toughness of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("toughness")
    public String getToughness() {
        return toughness;
    }

    // Setter method for the toughness of the card.
    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    // Getter method for the colors of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("colors")
    public List<String> getColors() {
        return colors;
    }

    // Setter method for the colors of the card.
    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    // Getter method for the color identity of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("color_identity")
    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    // Setter method for the color identity of the card.
    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    // Getter method for the prices of the card, annotated with JsonProperty for JSON mapping.
    @JsonProperty("prices")
    public Prices getPrices() {
        return prices;
    }

    // Setter method for the prices of the card.
    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    // Inner class to represent prices of the card.
    public static class Prices {
        private String eur;      // Price in euros.
        private String eurFoil;  // Price of the foil version in euros.

        // Getter method for the euro price, annotated with JsonProperty for JSON mapping.
        @JsonProperty("eur")
        public String getEur() {
            return eur;
        }

        // Setter method for the euro price.
        public void setEur(String eur) {
            this.eur = eur;
        }

        // Getter method for the foil euro price, annotated with JsonProperty for JSON mapping.
        @JsonProperty("eur_foil")
        public String getEurFoil() {
            return eurFoil;
        }

        // Setter method for the foil euro price.
        public void setEurFoil(String eurFoil) {
            this.eurFoil = eurFoil;
        }
    }
}
