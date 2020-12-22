with Ada.Text_IO;

use Ada.Text_IO;

procedure Lab1 is
   type ArrayOfInteger is array(Integer range<>) of Integer;

   arr: ArrayOfInteger(0..99);
   countOfPartOfArray: Integer := 2;

   function getSumPartOfArray (leftIndex, rightIndex : in Integer) return Integer;

   function getSumPartOfArray (leftIndex, rightIndex : in Integer) return Integer is
      result : Integer := 0;
   begin
      for i in leftIndex .. rightIndex loop
         result := result + arr(i);
      end loop;

      return result;
   end getSumPartOfArray;

   task type TaskSumOfPartOfArray is
      entry beginSumOfPartOfArray (leftIndex, rightIndex : in Integer);
      entry endSumOfPartOfArray (sumOfPartOfArray : out Integer);
   end;

   task body TaskSumOfPartOfArray is
      leftIndex, rightIndex, result : Integer;
   begin
      accept beginSumOfPartOfArray (leftIndex, rightIndex : Integer) do
         TaskSumOfPartOfArray.leftIndex := leftIndex;
         TaskSumOfPartOfArray.rightIndex := rightIndex;
      end beginSumOfPartOfArray;

      result := getSumPartOfArray(leftIndex, rightIndex);

      accept endSumOfPartOfArray (sumOfPartOfArray : out Integer) do
         sumOfPartOfArray := result;
      end endSumOfPartOfArray;
   end TaskSumOfPartOfArray;

   function getSum(arr : in ArrayOfInteger; length, countOfPartOfArray : in Integer) return Integer is
      leftIndex : Integer := arr'First;
      rightIndex, result, sumOfPartOfArray: Integer;

      lengthOfPartOfArray : Integer := length / countOfPartOfArray;
      tempArray : array (0 .. countOfPartOfArray - 1) of TaskSumOfPartOfArray;
   begin
      for i in tempArray'Range loop
         rightIndex := leftIndex + lengthOfPartOfArray;
         tempArray(i).beginSumOfPartOfArray(leftIndex, rightIndex - 1);
         leftIndex := rightIndex;
      end loop;

      result := getSumPartOfArray (leftIndex, arr'Last);

      for i in tempArray'Range loop
         tempArray(i).endSumOfPartOfArray(sumOfPartOfArray);
         result := result + sumOfPartOfArray;
      end loop;

      return result;
   end getSum;

begin
   for i in arr'First .. arr'Last loop
      arr(i) := Integer(i);
   end loop;

   Put("sum:");
   Put(getSum(arr, arr'Length, countOfPartOfArray)'Image);
end Lab1;