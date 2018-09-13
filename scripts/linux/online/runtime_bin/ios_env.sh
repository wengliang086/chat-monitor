#!bin/sh

CHANNEL_ARRAY=(
"haima"
"aisi"
"91"
"tongbu"
"xyaide"
"pp"
"itools"
"hoolai"
"guopanios"
"le8"
"dangleios"
"kuaiyong"
"07073ios"
"lehhios"
"mopin"
"xiao7"
)

check_channel(){
    if [ "$1" = "all" ];then
        return 0
    else
        for ((i=0; i<${#CHANNEL_ARRAY[@]}; i++ ));
        do
            attr=(${CHANNEL_ARRAY[$i]})
            if [ "$1" = "${attr[0]}" ];then
                return 0
            fi
        done
    fi
    return 1
}
