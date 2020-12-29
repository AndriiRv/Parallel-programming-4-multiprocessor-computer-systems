with Ada.Text_IO;

use Ada.Text_IO;

procedure lab1 is
   type ArrayOfInteger is array(Integer range<>) of Integer;

   arr: ArrayOfInteger(0..99);
   countOfPartOfArray: Integer := 2;

   function getSumPartOfArray (leftIndex, rightIndex : in Integer) return Integer;

   protected Server is
      procedure setResult(Current : in Integer);
      function getResult return Integer;
      entry Wait;

   private
      sum : Integer := 0;
      counterOfPartOfArray : Integer := 0;
   end;

   protected body Server is
      procedure setResult(Current : in Integer) is
      begin
         sum := sum + Current;
         counterOfPartOfArray := counterOfPartOfArray + 1;
      end setResult;

      function getResult return Integer is
      begin
         return sum;
      end getResult;

      entry Wait when counterOfPartOfArray = countOfPartOfArray is
      begin
         null;
      end Wait;
   end Server;

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
   end;

   task body TaskSumOfPartOfArray is
      leftIndex, rightIndex, result : Integer;
   begin
      accept beginSumOfPartOfArray (leftIndex, rightIndex : Integer) do
         TaskSumOfPartOfArray.leftIndex := leftIndex;
         TaskSumOfPartOfArray.rightIndex := rightIndex;
      end beginSumOfPartOfArray;

      result := getSumPartOfArray(leftIndex, rightIndex);

      Server.setResult(result);
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

      result := result + Server.getResult;

      Server.Wait;

      return result;
   end getSum;

begin
   for i in arr'First .. arr'Last loop
      arr(i) := Integer(i);
   end loop;

   Put("sum:");
   Put(getSum(arr, arr'Length, countOfPartOfArray)'Image);
end lab1;