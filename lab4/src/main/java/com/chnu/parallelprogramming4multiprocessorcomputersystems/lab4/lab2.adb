with Ada.Text_IO;

use Ada.Text_IO;

procedure Lab2 is
   type ArrayOfInteger is array(Integer range<>) of Integer;

   arr: ArrayOfInteger(0..10);
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
      tempArray : ArrayOfInteger(0 .. arr'Length);
      counter : Integer := 0;

      tempArr : ArrayOfInteger(0 .. arr'Length);

   begin
      loop
         tempArr(counter) := tempArr(counter) + tempArr(tempArr'Length - 1 - counter);
         counter := counter + 1;

         if(counter = tempArr'Length / 2) then
            tempArray := tempArr;

            tempArr := tempArray(tempArr'Length / 2);

            for i in 0 .. tempArr'Length loop
               tempArr(i) := tempArray(i);
            end loop;
            counter := 0;
         end if;

         if (arr'Length = 1) then
            return arr'First;
         end if;
      end loop;

    end getSum;

begin
   for i in arr'First .. arr'Last loop
      arr(i) := Integer(i);
   end loop;

   Put_Line("");
   Put_Line(getSum(arr)'Image);
end Lab2;
