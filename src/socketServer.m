t = tcpip('localhost',4012, 'NetworkRole', 'server');
fopen(t);
cnt = 0;
while(cnt<10)
    cnt = cnt+1;
    fwrite(t,10);
    pause(1);
end
fclose(t);