package utils;

public class MemberIdSeq {

    private static MemberIdSeq getInstance = null;
    private long memberId;

    private MemberIdSeq(){
        memberId = 0;
    }

    public static MemberIdSeq getGetInstance() {
        if(getInstance == null){
            getInstance = new MemberIdSeq();
        }
        return getInstance;
    }

    public long getMemberId() {
        return memberId++;
    }
}
