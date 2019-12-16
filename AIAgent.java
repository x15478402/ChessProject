import java.util.*;

public class AIAgent{
  Random rand;

  public AIAgent(){
    rand = new Random();
  }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

  public Move randomMove(Stack possibilities){

    int moveID = rand.nextInt(possibilities.size());
    System.out.println("Agent randomly selected move : "+moveID);
    for(int i=1;i < (possibilities.size()-(moveID));i++){
      possibilities.pop();
    }
    Move selectedMove = (Move)possibilities.pop();
    return selectedMove;
  }

  /*
    This strategy does really care about what happens once the move is made...

    This could mean that the AI agent could take a piece even though the player
    will immediately gain some advantage...

    The AI agent takes a pawn with his Queen and in response to this attack the
    player takes the queen (AI agents queen) with another pawn.

    AI after making the move is up one point as the pawn is worth 1
    however the Queen has a value of nine points and when the player takes
    the AI agents queen it is down eight points.

    Pawn: 1
    Knight / Bishop: 3
    Rook: 5
    Queen: 9
    King is worth the game.

    get all the possible moves just like above with the Random Agent and then
    apply a utility function to work out which move to make.
*/

  public Move nextBestMove(Stack whiteStack, Stack blackStack) {
    Stack whitePieces = (Stack) whiteStack.clone();
    Stack black = (Stack) blackStack.clone();
    Move whiteMove, currentMove, bestMove;
    Square blackPosition;
    int points = 0;
    int highestPoints = 0;
    bestMove = null;

    while (!whiteStack.empty()) { //while the stack is not empty
      whiteMove = (Move) whiteStack.pop(); //pop from stack
      currentMove = whiteMove;

      /*compare white landing position to black positions, if eating a piece is possible
      it takes it and if not it does a random move.
      */
      while (!black.isEmpty()) {
        points = 0;
        blackPosition = (Square) black.pop();
        if ((currentMove.getLanding().getXC() == blackPosition.getXC()) && (currentMove.getLanding().getYC() == blackPosition.getYC())) {

          //assigning points to black piece
          if(blackPosition.getName().contains("BlackPawn")) {
            points = 1;
          }
          else if(blackPosition.getName().contains("BlackBishop") || blackPosition.getName().contains("BlackKnight")) {
            points = 3;
          }
          else if(blackPosition.getName().contains("BlackRook")) {
            points = 5;
          }
          else if(blackPosition.getName().contains("BlackQueen")) {
            points = 9;
          }
          else if(blackPosition.getName().contains("BlackKing")) {
            points = 10;
            System.out.println("You have won");
          }
        }
        // updates the best next move
        if (points > highestPoints) {
          highestPoints = points;
          bestMove = currentMove;
        }
      }
      // checks the center of the board and assigns a point to the x axis 3/4 for a move
      if ((currentMove.getStart().getYC() < currentMove.getLanding().getYC())
      && (currentMove.getLanding().getXC() == 3) && (currentMove.getLanding().getYC() == 3)
      || (currentMove.getLanding().getXC() == 4) && (currentMove.getLanding().getYC() == 3)
      || (currentMove.getLanding().getXC() == 3) && (currentMove.getLanding().getYC() == 4)
      || (currentMove.getLanding().getXC() == 4) && (currentMove.getLanding().getYC() == 4)) {
        points = 1;

      // update bestMove
      if (points > highestPoints) {
        highestPoints = points;
        bestMove = currentMove;
      }
    }
    // reload the black squares
    black = (Stack) blackStack.clone();
  }
  // uses the best move available or just go random
  if (highestPoints > 0) { //returns best move
    System.out.println("Agent selected next best move");
    return bestMove;
  }
  System.out.println("Random move");
  return randomMove(whitePieces); //returns a random move
}

  /*
    This Agent extends the function of the agent above...this agent looks ahead and tries to determine what the player is going to do next...

    Sounds familiar...just like a min max routine.

    We know how to get the possible movements of all the pieces as we need this functionality for making random moves. We now need to be able to
    capture the movements / potential movements of the players pieces exactly as we did for the white pieces. Once we have this stack of movements
    we need a utility function to be able to calculated the value of the movements and then estimate which movement the player will make and then
    the agent responds to this movement.

    Random --> get all possible movements for white
          --> slect a random move.

    nextBestMove --> get all possible movements for white
                --> create a utility function based on the current move... this could be if we take a piece we score some points.
                --> loop through the stack of movements and check if we are taking a piece and if so make this movement

    twoLevelsDeep --> get all possible movements for white (stack)

                  then for each of these movements we find the best possible response for the player
                    --> get all possible movements for black (stack) after the board changes for each of the movements for white
                    --> rank these according to a utility function
                    --> the agent mkaes the best possible move that it can make with the least best response from the player.

  */

  public Move twoLevelsDeep(Stack possibilities){
    Move selectedMove = new Move();
    return selectedMove;
  }
}
