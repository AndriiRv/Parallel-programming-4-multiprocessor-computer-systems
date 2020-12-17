with Ada.Text_IO;

use Ada.Text_IO;

procedure Lab1 is
   type ArrayOfInteger is array(Integer range<>) of Integer;

   arr: ArrayOfInteger(0..99);
   countOfPartOfArray: Integer := 2;

   function getSumPartOfArray (arr : in ArrayOfInteger; leftIndex, rightIndex : in Integer) return Integer;

   function getSumPartOfArray (arr : in ArrayOfInteger; leftIndex, rightIndex : in Integer) return Integer is
      result : Integer := 0;
      tempRightIndex : Integer := 0;
   begin
      tempRightIndex := rightIndex;

      for i in leftIndex .. tempRightIndex loop
         result := result + arr(i);
      end loop;

      return result;
   end getSumPartOfArray;

   function getSum(arr : in ArrayOfInteger; length, countOfPartOfArray : in Integer) return Integer is
      leftIndex : Integer := 0;
      rightIndex : Integer := 0;
      lengthOfPartOfArray : Integer := length / countOfPartOfArray;

      result : Integer := 0;
      tempCountOfPart : Integer := countOfPartOfArray;
      tempLength : Integer := length;

   begin
      tempCountOfPart := tempCountOfPart - 1;

      for i in Integer range 0 .. tempCountOfPart - 1 loop
         rightIndex := leftIndex + lengthOfPartOfArray;

         result := result + getSumPartOfArray(arr, leftIndex, rightIndex);

         lengthOfPartOfArray := lengthOfPartOfArray + 1;
         leftIndex := leftIndex + lengthOfPartOfArray;
    end loop;

      if (tempCountOfPart <= 1) then
         tempLength := tempLength - 1;
         result := result + getSumPartOfArray(arr, leftIndex, tempLength);
      end if;

      return result;
    end getSum;

begin
   for i in arr'First .. arr'Last loop
      arr(i) := Integer(i);
   end loop;

   Put("sum:");
   Put(getSum(arr, arr'Length, countOfPartOfArray)'Image);
end Lab1;
