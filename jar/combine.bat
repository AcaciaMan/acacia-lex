rem 
rem Copyright 2012 Acacia Man
rem The program is distributed under the terms of the GNU General Public License
rem 
rem This file is part of acacia-lex.
rem
rem acacia-lex is free software: you can redistribute it and/or modify
rem it under the terms of the GNU General Public License as published by
rem the Free Software Foundation, either version 3 of the License, or
rem (at your option) any later version.
rem
rem acacia-lex is distributed in the hope that it will be useful,
rem but WITHOUT ANY WARRANTY; without even the implied warranty of
rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
rem GNU General Public License for more details.
rem 
rem You should have received a copy of the GNU General Public License
rem along with acacia-lex.  If not, see <http://www.gnu.org/licenses/>.
rem 

xcopy /y "..\Source\AcaciaAnn\dist\AcaciaAnn.jar" "AcaciaAnn.jar"
xcopy /y "..\Source\Acacia\dist\Acacia.jar" "Acacia.jar"
xcopy /y ""..\Source\AcaciaLex\dist\AcaciaLex.jar"" "AcaciaLex.jar"

cd tmp
C:\Java\jdk1.6.0_12\bin\jar -xf ../AcaciaAnn.jar
C:\Java\jdk1.6.0_12\bin\jar -xf ../Acacia.jar
C:\Java\jdk1.6.0_12\bin\jar -xf ../AcaciaLex.jar

cd ..
C:\Java\jdk1.6.0_12\bin\jar -cvf combined.jar -C tmp .
