
git config --global user.name %1
git config --global user.email %2
git config --global user.password %3


ECHO %1 %2 %3 %4 %5
xcopy /s /D %5 "C:\Users\rameshkumar.m\Desktop\code\sample\"%6\

cd C:\Users\rameshkumar.m\Desktop\code\sample
git.exe add .
git.exe commit -m "Initial Commit"
git.exe push