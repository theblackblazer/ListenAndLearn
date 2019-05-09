package com.example.listenandlearn;


// Qlist contains Getter and Setter Methods to Store Values and Pass to Adapter,
public class QList {
    private String qname;
    private int qno;

    public QList( int qno,String qname) {
        this.qname = qname;
        this.qno = qno;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public int getQno() {
        return qno;
    }

    public void setQno(int qno) {
        this.qno = qno;
    }
}
