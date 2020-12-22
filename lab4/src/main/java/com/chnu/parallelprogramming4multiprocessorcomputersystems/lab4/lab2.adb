with Ada.Text_IO, Ada.Integer_Text_IO;

use Ada.Text_IO;
use Ada.Integer_Text_IO;

procedure Lab2 is
   type ArrayOfInteger is array(Integer range<>) of Integer;

   arr: ArrayOfInteger(0..9);
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

   function getSum(arr : in ArrayOfInteger) return Integer is
      tempArray : ArrayOfInteger(0 .. 9);

      newTempArray : array (0 .. countOfPartOfArray - 1) of TaskSumOfPartOfArray;

      counter, innerCounter, outerCounter, tempVar, result, sumOfPartOfArray : Integer := 0;
   begin
      for i in arr'First..arr'Last loop
         tempArray(i) := Integer(i);
      end loop;

      tempVar := tempArray'Length / 2;
      loop
         counter := counter + 1;

         if (outerCounter = 0) then
            newTempArray(innerCounter).beginSumOfPartOfArray(innerCounter, tempArray'Length - 1 - innerCounter);
         else
            newTempArray(innerCounter).beginSumOfPartOfArray(innerCounter, tempArray'Length - 2 - outerCounter);
         end if;

         innerCounter := innerCounter + 1;

         if(innerCounter = tempVar) then
            loop
               tempArray(innerCounter) := 0;
               innerCounter := innerCounter + 1;
               outerCounter := outerCounter + 1;

               if (innerCounter = tempArray'Length - 1) then
                  exit;
               end if;

            end loop;

            if(outerCounter = arr'Length) then
               outerCounter := 0;
            end if;

            tempVar := (tempVar / 2) + 1;
            innerCounter := 0;
         end if;

         if (counter > tempArray'Length) then
            for i in tempArray'Range loop
               newTempArray(i).endSumOfPartOfArray(sumOfPartOfArray);
               result := result + sumOfPartOfArray;
            end loop;
            Put("result:");
            Put(result);
            return result;
         end if;

      end loop;

    end getSum;

begin
   for i in 0 .. 9 loop
      arr(i) := Integer(i);
   end loop;

   Put("sum:");
   Put(getSum(arr)'Image);
end Lab2;