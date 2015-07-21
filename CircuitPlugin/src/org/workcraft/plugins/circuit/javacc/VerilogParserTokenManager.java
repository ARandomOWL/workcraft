/* Generated By:JavaCC: Do not edit this line. VerilogParserTokenManager.java */
package org.workcraft.plugins.circuit.javacc;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import org.workcraft.plugins.circuit.verilog.Module;
import org.workcraft.plugins.circuit.verilog.Port;
import org.workcraft.plugins.circuit.verilog.Instance;
import org.workcraft.plugins.circuit.verilog.Pin;

/** Token Manager. */
public class VerilogParserTokenManager implements VerilogParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa1_1(0x200L);
      default :
         return 1;
   }
}
private int jjMoveStringLiteralDfa1_1(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 110:
         return jjMoveStringLiteralDfa2_1(active0, 0x200L);
      default :
         return 2;
   }
}
private int jjMoveStringLiteralDfa2_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 2;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 2;
   }
   switch(curChar)
   {
      case 100:
         return jjMoveStringLiteralDfa3_1(active0, 0x200L);
      default :
         return 3;
   }
}
private int jjMoveStringLiteralDfa3_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 3;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 3;
   }
   switch(curChar)
   {
      case 115:
         return jjMoveStringLiteralDfa4_1(active0, 0x200L);
      default :
         return 4;
   }
}
private int jjMoveStringLiteralDfa4_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 4;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 4;
   }
   switch(curChar)
   {
      case 112:
         return jjMoveStringLiteralDfa5_1(active0, 0x200L);
      default :
         return 5;
   }
}
private int jjMoveStringLiteralDfa5_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 5;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 5;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa6_1(active0, 0x200L);
      default :
         return 6;
   }
}
private int jjMoveStringLiteralDfa6_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 6;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 6;
   }
   switch(curChar)
   {
      case 99:
         return jjMoveStringLiteralDfa7_1(active0, 0x200L);
      default :
         return 7;
   }
}
private int jjMoveStringLiteralDfa7_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 7;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 7;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa8_1(active0, 0x200L);
      default :
         return 8;
   }
}
private int jjMoveStringLiteralDfa8_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 8;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 8;
   }
   switch(curChar)
   {
      case 102:
         return jjMoveStringLiteralDfa9_1(active0, 0x200L);
      default :
         return 9;
   }
}
private int jjMoveStringLiteralDfa9_1(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 9;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 9;
   }
   switch(curChar)
   {
      case 121:
         if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(9, 9);
         break;
      default :
         return 10;
   }
   return 10;
}
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0xfe180L) != 0L)
         {
            jjmatchedKind = 20;
            return 15;
         }
         if ((active0 & 0x6000000L) != 0L)
         {
            jjmatchedKind = 27;
            return 14;
         }
         return -1;
      case 1:
         if ((active0 & 0x6000000L) != 0L)
         {
            if (jjmatchedPos == 0)
            {
               jjmatchedKind = 27;
               jjmatchedPos = 0;
            }
            return -1;
         }
         if ((active0 & 0xfe180L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 1;
            return 15;
         }
         return -1;
      case 2:
         if ((active0 & 0x40000L) != 0L)
            return 15;
         if ((active0 & 0x6000000L) != 0L)
         {
            if (jjmatchedPos == 0)
            {
               jjmatchedKind = 27;
               jjmatchedPos = 0;
            }
            return -1;
         }
         if ((active0 & 0xbe180L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 2;
            return 15;
         }
         return -1;
      case 3:
         if ((active0 & 0x80000L) != 0L)
            return 15;
         if ((active0 & 0x3e180L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 3;
            return 15;
         }
         if ((active0 & 0x6000000L) != 0L)
         {
            if (jjmatchedPos == 0)
            {
               jjmatchedKind = 27;
               jjmatchedPos = 0;
            }
            return -1;
         }
         return -1;
      case 4:
         if ((active0 & 0x28000L) != 0L)
            return 15;
         if ((active0 & 0x16180L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 4;
            return 15;
         }
         return -1;
      case 5:
         if ((active0 & 0x12000L) != 0L)
            return 15;
         if ((active0 & 0x4180L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 5;
            return 15;
         }
         return -1;
      case 6:
         if ((active0 & 0x4100L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 6;
            return 15;
         }
         if ((active0 & 0x80L) != 0L)
            return 15;
         return -1;
      case 7:
         if ((active0 & 0x4100L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 7;
            return 15;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 28);
      case 41:
         return jjStopAtPos(0, 29);
      case 44:
         return jjStopAtPos(0, 32);
      case 46:
         return jjStopAtPos(0, 34);
      case 49:
         return jjMoveStringLiteralDfa1_0(0x6000000L);
      case 59:
         return jjStopAtPos(0, 33);
      case 91:
         return jjStopAtPos(0, 30);
      case 93:
         return jjStopAtPos(0, 31);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x28000L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x10000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x80L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      default :
         return jjMoveNfa_0(2, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 39:
         return jjMoveStringLiteralDfa2_0(active0, 0x6000000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x2c000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 112:
         return jjMoveStringLiteralDfa2_0(active0, 0x80L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x100L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x6000000L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x6000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x80L);
      case 103:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(2, 18, 15);
         break;
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x100L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      case 116:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 48:
         if ((active0 & 0x2000000L) != 0L)
            return jjStopAtPos(3, 25);
         break;
      case 49:
         if ((active0 & 0x4000000L) != 0L)
            return jjStopAtPos(3, 26);
         break;
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x80L);
      case 101:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(3, 19, 15);
         break;
      case 109:
         return jjMoveStringLiteralDfa4_0(active0, 0x4100L);
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x2a000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x180L);
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000L);
      case 116:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(4, 15, 15);
         else if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(4, 17, 15);
         break;
      case 117:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x4000L);
      case 101:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(5, 13, 15);
         break;
      case 102:
         return jjMoveStringLiteralDfa6_0(active0, 0x80L);
      case 116:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(5, 16, 15);
         return jjMoveStringLiteralDfa6_0(active0, 0x100L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa7_0(active0, 0x100L);
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0x4000L);
      case 121:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(6, 7, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 108:
         return jjMoveStringLiteralDfa8_0(active0, 0x4000L);
      case 118:
         return jjMoveStringLiteralDfa8_0(active0, 0x100L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(8, 8, 15);
         else if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(8, 14, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 15;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 27)
                        kind = 27;
                     jjCheckNAdd(14);
                  }
                  else if (curChar == 34)
                     jjCheckNAddStates(0, 2);
                  else if (curChar == 47)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(8);
                  }
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 15:
                  if ((0x7ff800000000000L & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(8);
                  }
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(6);
                  }
                  break;
               case 0:
                  if (curChar != 47)
                     break;
                  if (kind > 5)
                     kind = 5;
                  jjCheckNAdd(1);
                  break;
               case 1:
                  if ((0xffffffffffffdbffL & l) == 0L)
                     break;
                  if (kind > 5)
                     kind = 5;
                  jjCheckNAdd(1);
                  break;
               case 4:
                  if ((0xffffffffffffdbffL & l) == 0L)
                     break;
                  if (kind > 6)
                     kind = 6;
                  jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(6);
                  break;
               case 7:
                  if (curChar != 47)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(8);
                  break;
               case 8:
                  if ((0x7ff800000000000L & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(8);
                  break;
               case 9:
               case 12:
                  if (curChar == 34)
                     jjCheckNAddStates(0, 2);
                  break;
               case 10:
                  if ((0xfffffffb00000000L & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 13:
                  if (curChar == 34 && kind > 22)
                     kind = 22;
                  break;
               case 14:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 27)
                     kind = 27;
                  jjCheckNAdd(14);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((0x7fffffe97fffffeL & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(8);
                  }
                  else if (curChar == 96)
                  {
                     if (kind > 6)
                        kind = 6;
                     jjCheckNAdd(4);
                  }
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(6);
                  }
                  break;
               case 15:
                  if ((0x7fffffe97fffffeL & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(8);
                  }
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(6);
                  }
                  break;
               case 1:
                  if (kind > 5)
                     kind = 5;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 3:
                  if (curChar != 96)
                     break;
                  if (kind > 6)
                     kind = 6;
                  jjCheckNAdd(4);
                  break;
               case 4:
                  if (kind > 6)
                     kind = 6;
                  jjCheckNAdd(4);
                  break;
               case 5:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(6);
                  break;
               case 6:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(6);
                  break;
               case 7:
                  if ((0x7fffffe97fffffeL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(8);
                  break;
               case 8:
                  if ((0x7fffffe97fffffeL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(8);
                  break;
               case 10:
                  if ((0x7fffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 11:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 12:
                  if ((0x400010000000L & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 5)
                     kind = 5;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 4:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 6)
                     kind = 6;
                  jjstateSet[jjnewStateCnt++] = 4;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 15 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_2()
{
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa1_2(0x800L);
      default :
         return 1;
   }
}
private int jjMoveStringLiteralDfa1_2(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 110:
         return jjMoveStringLiteralDfa2_2(active0, 0x800L);
      default :
         return 2;
   }
}
private int jjMoveStringLiteralDfa2_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 2;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 2;
   }
   switch(curChar)
   {
      case 100:
         return jjMoveStringLiteralDfa3_2(active0, 0x800L);
      default :
         return 3;
   }
}
private int jjMoveStringLiteralDfa3_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 3;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 3;
   }
   switch(curChar)
   {
      case 112:
         return jjMoveStringLiteralDfa4_2(active0, 0x800L);
      default :
         return 4;
   }
}
private int jjMoveStringLiteralDfa4_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 4;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 4;
   }
   switch(curChar)
   {
      case 114:
         return jjMoveStringLiteralDfa5_2(active0, 0x800L);
      default :
         return 5;
   }
}
private int jjMoveStringLiteralDfa5_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 5;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 5;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa6_2(active0, 0x800L);
      default :
         return 6;
   }
}
private int jjMoveStringLiteralDfa6_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 6;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 6;
   }
   switch(curChar)
   {
      case 109:
         return jjMoveStringLiteralDfa7_2(active0, 0x800L);
      default :
         return 7;
   }
}
private int jjMoveStringLiteralDfa7_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 7;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 7;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa8_2(active0, 0x800L);
      default :
         return 8;
   }
}
private int jjMoveStringLiteralDfa8_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 8;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 8;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa9_2(active0, 0x800L);
      default :
         return 9;
   }
}
private int jjMoveStringLiteralDfa9_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 9;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 9;
   }
   switch(curChar)
   {
      case 105:
         return jjMoveStringLiteralDfa10_2(active0, 0x800L);
      default :
         return 10;
   }
}
private int jjMoveStringLiteralDfa10_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 10;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 10;
   }
   switch(curChar)
   {
      case 118:
         return jjMoveStringLiteralDfa11_2(active0, 0x800L);
      default :
         return 11;
   }
}
private int jjMoveStringLiteralDfa11_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return 11;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 11;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x800L) != 0L)
            return jjStopAtPos(11, 11);
         break;
      default :
         return 12;
   }
   return 12;
}
static final int[] jjnextStates = {
   10, 11, 13,
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null,
"\155\157\144\165\154\145", "\145\156\144\155\157\144\165\154\145", "\151\156\160\165\164",
"\157\165\164\160\165\164", "\151\156\157\165\164", "\162\145\147", "\167\151\162\145", null, null, null,
null, null, "\61\47\142\60", "\61\47\142\61", null, "\50", "\51", "\133", "\135",
"\54", "\73", "\56", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "WITHIN_SPECIFY",
   "WITHIN_PRIMITIVE",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, 1, 2, 0, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
};
static final long[] jjtoToken = {
   0x7fe7fe001L,
};
static final long[] jjtoSkip = {
   0x1ffeL,
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[15];
private final int[] jjstateSet = new int[30];
protected char curChar;
/** Constructor. */
public VerilogParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public VerilogParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 15; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 3 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken()
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   switch(curLexState)
   {
     case 0:
       try { input_stream.backup(0);
          while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       if (jjmatchedPos == 0 && jjmatchedKind > 10)
       {
          jjmatchedKind = 10;
       }
       break;
     case 2:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_2();
       if (jjmatchedPos == 0 && jjmatchedKind > 12)
       {
          jjmatchedKind = 12;
       }
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
