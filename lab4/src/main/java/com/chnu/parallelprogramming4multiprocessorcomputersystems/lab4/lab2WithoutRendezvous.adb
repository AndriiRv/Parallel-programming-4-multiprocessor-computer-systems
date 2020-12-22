with Ada.Text_IO, Ada.Integer_Text_IO;

use Ada.Text_IO;
use Ada.Integer_Text_IO;

procedure Lab2 is
   type ArrayOfInteger is array(Integer range<>) of Integer;

   arr: ArrayOfInteger(0..9);
   countOfPartOfArray: Integer := 2;

   function getSumPartOfArray (arr : in ArrayOfInteger; leftIndex, rightIndex : in Integer) return Integer;

   function getSumPartOfArray (arr : in ArrayOfInteger; leftIndex, rightIndex : in Integer) return Integer is
      result : Integer := 0;
   begin
      for i in leftIndex .. rightIndex loop
         result := result + arr (i);
      end loop;

      return result;
   end getSumPartOfArray;

   function getSum(arr : in ArrayOfInteger) return Integer is
      tempArray : ArrayOfInteger(0 .. 9);
      counter, innerCounter, outerCounter, tempVar : Integer := 0;
   begin
      for i in arr'First .. arr'Last loop
         tempArray(i) := Integer(i);
      end loop;

      tempVar := tempArray'Length / 2;
      loop
         counter := counter + 1;

         if (outerCounter = 0) then
            tempArray(innerCounter) := tempArray(innerCounter) + tempArray(tempArray'Length - 1 - innerCounter);
         else
            tempArray(innerCounter) := tempArray(innerCounter) + tempArray(tempArray'Length - 2 - outerCounter);
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
            return tempArray(0);
         end if;

      end loop;

    end getSum;

begin
   for i in 0 .. 9 loop
      arr(i) := Integer(i);
   end loop;
end Lab2;