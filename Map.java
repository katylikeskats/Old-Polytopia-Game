//Have a map checker later to check if the random map is usable

import java.util.Random; //IMPLEMENT THIS INSTEAD OF MATH.RANDOM

class Map {
  
  private static int MAP_LENGTH;
  private Space[][] tileMap;
  
  public Map(int length) {
    MAP_LENGTH = length; //We could also input number of players and calculate map length based on that
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
          randC = (int)Math.round(Math.random()*((MAP_LENGTH-1)/4));
          randR = ((int)Math.round(Math.random()*((MAP_LENGTH-1)/4))) + ((MAP_LENGTH-1)*3/4);
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
    
    //Adding Cities
    addCities();
    
    //Adding Items
    addItems();
    
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
          if (tileMap[i][j].getTerrain() instanceof Grass) {
            numLandTiles++;
          }
        }
      }
    } else if (quartile == 1) {
      for (int i = MAP_LENGTH/2; i < MAP_LENGTH; i++) {
        for (int j = 0; j < MAP_LENGTH/2; j++) {
          if (tileMap[i][j].getTerrain() instanceof Grass) {
            numLandTiles++;
          }
        }
      }
    } else if (quartile == 2) {
      for (int i = 0; i < MAP_LENGTH/2; i++) {
        for (int j = MAP_LENGTH/2; j < MAP_LENGTH; j++) {
          if (tileMap[i][j].getTerrain() instanceof Grass) {
            numLandTiles++;
          }
        }
      }
    } else {
      for (int i = MAP_LENGTH/2; i < MAP_LENGTH; i++) {
        for (int j = MAP_LENGTH/2; j < MAP_LENGTH; j++) {
          if (tileMap[i][j].getTerrain() instanceof Grass) {
            numLandTiles++;
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
        if (tileMap[i][j].getTerrain() instanceof Grass) {
          if (((Math.abs(cityR - i)) <= 2) && ((Math.abs(cityC - j)) <= 2)) { //Maybe change condition format
            return true;
          }
        }
      }
    }
    return false;
  }
  
  private void addItems() { //Maybe too many items? Othewise it's fine
    int random3 = 0;
    for (int i = 0; i < MAP_LENGTH; i++) {
      for (int j = 0; j < MAP_LENGTH; j++) {
        if ((tileMap[i][j].getCity() != null) && (adjacentCity(i, j))) { //Only have an item if possibly within city borders
          random3 = (int)(Math.round(Math.random()*9));
          if (tileMap[i][j].getTerrain() instanceof Grass) { //Land items
            if (random3 == 0) {
              tileMap[i][j].setResource(new Fruit(i,j)); //Rep Fruit
            } else if (random3 == 1) {
              tileMap[i][j].setResource(new Animal(i,j)); //Rep Animal
            } else if (random3 == 2) {
              tileMap[i][j].setResource(new Forest(i,j)); //Rep Trees (forest)
            } else if (random3 == 3) {
              tileMap[i][j].setResource(new Crop(i,j)); //Rep Crop
            }
          } else if (tileMap[i][j] == null) {
            if (random3 < 2) {
              tileMap[i][j] = new Space(new Water(i, j), new Fish(i, j)); //Rep fish
            } else {
              tileMap[i][j] = new Space(new Water(i, j));
            }
          }
        }
      }
    }
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