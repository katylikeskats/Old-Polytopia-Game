//Have a map checker later to check if the random map is usable

import java.util.Random; //IMPLEMENT THIS INSTEAD OF MATH.RANDOM

class Map {

    private static int MAP_LENGTH;
    private int numPlayers;
    private Space[][] tileMap;

    public Map(int length, int numPlayers) {
        MAP_LENGTH = length; //We could also input number of players and calculate map length based on that
        this.numPlayers = numPlayers;
        tileMap = new Space[MAP_LENGTH][MAP_LENGTH];

        int randR = 0;
        int randC = 0;

        int lowerC;
        int higherC;
        int lowerR;
        int higherR;
        int random = 0;

        //Creating land
        for (int i = 0; i < 4; i++) {
            do {
                if (i == 0) {
                    lowerR = 0;
                    higherR = (MAP_LENGTH)/2 - 1;
                    lowerC = 0;
                    higherC = (MAP_LENGTH)/2 - 1;
                    randR = (int)Math.round(Math.random()*((MAP_LENGTH-1)/4));
                    randC = (int)Math.round(Math.random()*((MAP_LENGTH-1)/4));
                } else if (i == 1) {
                    lowerR = 0;
                    higherR = (MAP_LENGTH)/2 - 1;
                    lowerC = (MAP_LENGTH)/2 - 1;
                    higherC = MAP_LENGTH-1;
                    randR = (int)Math.round(Math.random()*((MAP_LENGTH-1)/4));
                    randC = ((int)Math.round(Math.random()*((MAP_LENGTH-1)/4))) + ((MAP_LENGTH-1)*3/4);
                } else if (i == 2) {
                    lowerR = (MAP_LENGTH)/2 - 1;
                    higherR = MAP_LENGTH-1;
                    lowerC = 0;
                    higherC = (MAP_LENGTH)/2 - 1;
                    randR = ((int)Math.round(Math.random()*((MAP_LENGTH-1)/4))) + ((MAP_LENGTH-1)*3/4);
                    randC = (int)Math.round(Math.random()*((MAP_LENGTH-1)/4));
                } else {
                    lowerR = (MAP_LENGTH)/2 - 1;
                    higherR = MAP_LENGTH-1;
                    lowerC = (MAP_LENGTH)/2 - 1;
                    higherC = MAP_LENGTH-1;
                    randR = ((int)Math.round(Math.random()*((MAP_LENGTH-1)/4))) + ((MAP_LENGTH-1)*3/4);
                    randC = ((int)Math.round(Math.random()*((MAP_LENGTH-1)/4))) + ((MAP_LENGTH-1)*3/4);
                }
                expandLand(randR, randC, 0, lowerR, higherR, lowerC, higherC);
            } while (!checkCoverage(i));

        }

        //Adding Water
        addWater();

        //Adding Cities
        addCities();

        //Adding Items
        addItems();
        
        //Adding Mountains
        addMountains();
        
        //Set the capital cities (number of which depends on the number of players), which are the players' starting cities
        //A tribe (int) is also assigned to each capital city (other cities are given tribes when captured by a player)
        assignCapitals();

    }

    private void expandLand(int r, int c, int choice, int lowerR, int higherR, int lowerC, int higherC) {
        tileMap[r][c] = new Space(new Grass(r, c));
        choice = (int)(Math.round(Math.random()*20));
        if (choice < 5) {
            if (c < higherC) {
                expandLand(r, c + 1, choice, lowerR, higherR, lowerC, higherC);
            } else {
                choice = (int)Math.round(Math.random()*20);
            }
        }
        if ((choice > 4) && (choice < 10)) {
            if (c > lowerC) {
                expandLand(r, c - 1, choice, lowerR, higherR, lowerC, higherC);
            } else {
                choice = (int)Math.round(Math.random()*20);
            }
        }
        if ((choice > 9) && (choice < 15)) {
            if (r < higherC) {
                expandLand(r + 1, c, choice, lowerR, higherR, lowerC, higherC);
            } else {
                choice = (int)Math.round(Math.random()*20);
            }
        }
        if ((choice > 14) && (choice < 20)) {
            if (r > lowerC) {
                expandLand(r - 1, c, choice, lowerR, higherR, lowerC, higherC);
            } else {
                choice = (int)Math.round(Math.random()*20);
            }
        }
        if (choice == 20) {
            return;
        }
    }

    private boolean checkCoverage(int quartile) {
        int numLandTiles = 0;
        int numTiles = (int)(Math.pow((MAP_LENGTH/2),2));
        if (quartile == 0) {
            for (int i = 0; i < MAP_LENGTH/2; i++) {
                for (int j = 0; j < MAP_LENGTH/2; j++) {
                    if (tileMap[i][j] != null) {
                        if (tileMap[i][j].getTerrain() instanceof Grass) {
                            numLandTiles++;
                        }
                    }
                }
            }
        } else if (quartile == 1) {
            for (int i = 0; i < MAP_LENGTH/2; i++) {
                for (int j = MAP_LENGTH/2; j < MAP_LENGTH; j++) {
                    if (tileMap[i][j] != null) {
                        if (tileMap[i][j].getTerrain() instanceof Grass) {
                            numLandTiles++;
                        }
                    }
                }
            }
        } else if (quartile == 2) {
            for (int i = MAP_LENGTH/2; i < MAP_LENGTH; i++) {
                for (int j = 0; j < MAP_LENGTH/2; j++) {
                    if (tileMap[i][j] != null) {
                        if (tileMap[i][j].getTerrain() instanceof Grass) {
                            numLandTiles++;
                        }
                    }
                }
            }
        } else {
            for (int i = MAP_LENGTH/2; i < MAP_LENGTH; i++) {
                for (int j = MAP_LENGTH/2; j < MAP_LENGTH; j++) {
                    if (tileMap[i][j] != null) {
                        if (tileMap[i][j].getTerrain() instanceof Grass) {
                            numLandTiles++;
                        }
                    }
                }
            }
        }
        if (numLandTiles > 0.4*numTiles) {
            return true;
        } else {
            return false;
        }
    }

    private void addCities() {
        int random2 = 0;
        for (int i = 1; i < MAP_LENGTH-1; i++) {
            for (int j = 1; j < MAP_LENGTH-1; j++) {
                if (tileMap[i][j].getTerrain() instanceof Grass) {
                    random2 = (int)(Math.round(Math.random()*18));
                    if (!adjacentCity(i, j) && (random2 < 3)) {
                        tileMap[i][j].setCity(new City(i, j, false)); //DETRMINE CAPITAL CITY in a dif method - Can do quartile check, in each one find a city surrounded by at lest 5 land tiles
                    }
                }
            }
        }
    }

    private boolean adjacentCity(int cityR, int cityC) {
        for (int i = 0; i < MAP_LENGTH; i++) {
            for (int j = 0; j < MAP_LENGTH; j++) {
                if (tileMap[i][j] != null) {
                    if (tileMap[i][j].getCity() != null) {
                        if (((Math.abs(cityR - i)) <= 2) && ((Math.abs(cityC - j)) <= 2)) { //Maybe change condition format
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void addWater() {
        for (int i = 0; i < MAP_LENGTH; i++) {
            for (int j = 0; j < MAP_LENGTH; j++) {
                if (tileMap[i][j] == null) {
                    tileMap[i][j] = new Space(new Water(i, j));
                }
            }
        }
    }

    private void addItems() { //Maybe too many items? Othewise it's fine
        int random3 = 0;
        for (int i = 0; i < MAP_LENGTH; i++) {
            for (int j = 0; j < MAP_LENGTH; j++) {
                random3 = (int)(Math.round(Math.random()*9));
                if (tileMap[i][j].getTerrain() instanceof Grass) {
                    if (adjacentCity(i, j) && tileMap[i][j].getCity() == null) { //Only have an item if possibly within city borders
                        if (random3 == 0) {
                            tileMap[i][j].setResource(new Fruit(i,j)); //Rep Fruit
                        } else if (random3 == 1) {
                            tileMap[i][j].setResource(new Animal(i,j)); //Rep Animal
                        } else if (random3 == 2) {
                            tileMap[i][j].setResource(new Forest(i,j)); //Rep Trees (forest)
                        } else if (random3 == 3) {
                            tileMap[i][j].setResource(new Crop(i,j)); //Rep Crop
                        }
                    }
                } else {
                    if (adjacentCity(i, j)) {
                        if (random3 < 2) {
                          tileMap[i][j].setResource(new Fish(i, j)); //Rep fish
                        } else if (random3 == 4) {
                          tileMap[i][j].setResource(new Whale(i, j)); //Rep whale
                        }
                    }
                }
            }
        }
    }
    
    private void addMountains() {
      int randRow = 0; 
      int randColumn = 0;
      int randValue = 0;
      do {
        randRow = (int)(Math.round(Math.random()*(MAP_LENGTH-4))) + 2;
        randColumn = (int)(Math.round(Math.random()*(MAP_LENGTH-4))) + 2;
        for (int i = (randRow - 2); i < (randRow + 2); i++) {
          for (int j = (randColumn - 2); j < (randColumn + 2); j++) {
            if ((tileMap[i][j].getTerrain() instanceof Grass) && (tileMap[i][j].isEmpty())) {
              randValue = (int)(Math.round(Math.random()));
              if (randValue == 0) {
                tileMap[i][j] = new Space(new Mountain(i, j));
              }
            }
          }
        }
      } while (!checkMountainCoverage());
    }
    
    private boolean checkMountainCoverage() {
      int numLandTiles = 0;
      int numMountains = 0;
      for (int i = 0; i < MAP_LENGTH; i++) {
        for (int j = 0; j < MAP_LENGTH; j++) {
          if (!(tileMap[i][j].getTerrain() instanceof Water)) {
            numLandTiles++; //Increase count of the number of land tiles in total
            if (tileMap[i][j].getTerrain() instanceof Mountain) {
              numMountains++; //Increase count of the number of mountain tiles
            }
          }
        }
      }
      if (numMountains > 0.15*numLandTiles) {
        return true;
      } else {
        return false;
      }
    }
    
    //Sets the capital city for each sector (number of sectors determined by number of players), and sets the tribes for those cities as well
    private void assignCapitals() {
      City bestCity = null;
      int mostLandTilesSurrounding = 0;
      int landTilesSurrounding = 0;
      int minRow = 0;
      int maxRow = 0;
      int minColumn = 0;
      int maxColumn = 0;
      if (numPlayers == 2) {
        for (int i = 0; i < 2; i++) {
          mostLandTilesSurrounding = 0;
          if (i == 0) {
            minRow = 0; 
            maxRow = MAP_LENGTH-1;
            minColumn = 0; 
            maxColumn = MAP_LENGTH/2;
          } else if (i == 1) {
            minRow = 0; 
            maxRow = MAP_LENGTH-1;
            minColumn = MAP_LENGTH/2;; 
            maxColumn = MAP_LENGTH-1;
          }
          for (int r = minRow; r < maxRow; r++) {
            for (int c = minColumn; c < maxColumn; c++) {
              if (tileMap[r][c].getCity() != null) {
                landTilesSurrounding = surroundingLandCheckCity(tileMap[r][c].getCity());
                if (landTilesSurrounding > mostLandTilesSurrounding) {
                  mostLandTilesSurrounding = landTilesSurrounding;
                  bestCity = tileMap[r][c].getCity();
                }
              }
            }
          }
          tileMap[bestCity.getR()][bestCity.getC()].getCity().setTribe(i);
          tileMap[bestCity.getR()][bestCity.getC()].getCity().setCapital(true);
          System.out.println("Capital city of player " + i + " is at " + bestCity.getC() + ", " + bestCity.getR()); //For checking capital city determination
        }
      } else if (numPlayers == 3) {
        //Are we doing this
      } else if (numPlayers == 4) {
        for (int i = 0; i < 4; i++) {
          mostLandTilesSurrounding = 0;
          if (i == 0) {
            minRow = 0;
            maxRow = MAP_LENGTH/2;
            minColumn = 0;
            maxColumn = MAP_LENGTH/2;
          } else if (i == 1) {
            minRow = 0;
            maxRow = MAP_LENGTH/2;
            minColumn = MAP_LENGTH/2;
            maxColumn = MAP_LENGTH-1;
          } else if (i == 2) {
            minRow = MAP_LENGTH/2;
            maxRow = MAP_LENGTH-1;
            minColumn = 0;
            maxColumn = MAP_LENGTH/2;
          } else if (i == 3) {
            minRow = MAP_LENGTH/2;
            maxRow = MAP_LENGTH-1;
            minColumn = MAP_LENGTH/2;
            maxColumn = MAP_LENGTH-1;
          }
          for (int r = minRow; r < maxRow; r++) {
            for (int c = minColumn; c < maxColumn; c++) {
              if (tileMap[r][c].getCity() != null) {
                landTilesSurrounding = surroundingLandCheckCity(tileMap[r][c].getCity());
                if (landTilesSurrounding > mostLandTilesSurrounding) {
                  mostLandTilesSurrounding = landTilesSurrounding;
                  bestCity = tileMap[r][c].getCity();
                }
              }
            }
          }
          tileMap[bestCity.getR()][bestCity.getC()].getCity().setTribe(i);
          tileMap[bestCity.getR()][bestCity.getC()].getCity().setCapital(true);
          System.out.println("Capital city of player " + i + " is at " + bestCity.getC() + ", " + bestCity.getR()); //For checking capital city determination
        }
      }
    }
    
    //Count the number of land tiles around a city; the city with the most land tiles around it in a section will be assigned the capital city of the tribe starting in said section
    private int surroundingLandCheckCity(City city) {
      int numLandTilesSurrounding = 0;
      for (int i = city.getR() - 1; i < city.getR() + 2; i++) {
        for (int j = city.getC() - 1; j < city.getC() + 2; j++) {
          if (tileMap[i][j].getTerrain() instanceof Grass) {
            numLandTilesSurrounding++;
          }
        }
      }
      return numLandTilesSurrounding;
    }

    public void printMap() {
        for (int i = 0; i < MAP_LENGTH; i++) {
            for (int j = 0; j < MAP_LENGTH; j++) {
                System.out.print(tileMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public Space[][] getMap() {
        return tileMap;
    }

}
