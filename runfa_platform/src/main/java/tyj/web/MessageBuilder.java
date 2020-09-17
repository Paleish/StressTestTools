package tyj.web;

import com.google.protobuf.ByteString;
import com.kys.pb.PbGate;

public class MessageBuilder {

    public static PbGate.C2S buildMsg(int sid, int cid, ByteString bs) {
        PbGate.C2S.Builder builder = PbGate.C2S.newBuilder();
        builder.setSid(sid);
        builder.setCid(cid);
        builder.setSequence(0);
        builder.setBody(bs);
        return builder.build();
    }
}
