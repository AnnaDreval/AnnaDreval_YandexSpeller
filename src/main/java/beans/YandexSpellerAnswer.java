package beans;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class YandexSpellerAnswer {

    @SerializedName("code")
    //@Expose
    public Integer code;
    @SerializedName("pos")
    //@Expose
    public Integer pos;
    @SerializedName("row")
    //@Expose
    public Integer row;
    @SerializedName("col")
    //@Expose
    public Integer col;
    @SerializedName("len")
    //@Expose
    public Integer len;
    @SerializedName("word")
    //@Expose
    public String word;
    @SerializedName("s")
    //@Expose
    public List<String> s = new ArrayList<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("pos", pos)
                .append("row", row)
                .append("col", col)
                .append("len", len)
                .append("word", word)
                .append("s", s).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(code)
                .append(pos)
                .append(row)
                .append(col)
                .append(len)
                .append(word)
                .append(s).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof YandexSpellerAnswer)) return false;

        YandexSpellerAnswer rhs = ((YandexSpellerAnswer) obj);
        return new EqualsBuilder()
                .append(code, rhs.code)
                .append(pos, rhs.pos)
                .append(row, rhs.row)
                .append(col, rhs.col)
                .append(len, rhs.len)
                .append(word, rhs.word)
                .append(s, rhs.s).isEquals();
    }
}
