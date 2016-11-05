//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Monthly_report_revision_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Monthly_report_revision> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Monthly_report_revision.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param bajon_bangou Data type: int
         * @param shain_kodo Data type: int
         * @param repoto_no_getsudo Data type: int
         * @param repoto_no_nendo Data type: int
         *
         */
        public Pk(final int bajon_bangou, final int shain_kodo, final int repoto_no_getsudo, final int repoto_no_nendo) {
            this.bajon_bangou = bajon_bangou;
            this.shain_kodo = shain_kodo;
            this.repoto_no_getsudo = repoto_no_getsudo;
            this.repoto_no_nendo = repoto_no_nendo;
        }

        @Column(name = "BAJON_BANGOU")
        protected int bajon_bangou;

        public int getBajon_bangou() {
            return this.bajon_bangou;
        }

        @Column(name = "SHAIN_KODO")
        protected int shain_kodo;

        public int getShain_kodo() {
            return this.shain_kodo;
        }

        @Column(name = "REPOTO_NO_GETSUDO")
        protected int repoto_no_getsudo;

        public int getRepoto_no_getsudo() {
            return this.repoto_no_getsudo;
        }

        @Column(name = "REPOTO_NO_NENDO")
        protected int repoto_no_nendo;

        public int getRepoto_no_nendo() {
            return this.repoto_no_nendo;
        }
    }

    //default constructor
    public Monthly_report_revision_MAPPED() {
    }

    protected Monthly_report_revision_MAPPED(final int bajon_bangou, final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, final int repoto_no_getsudo, final int repoto_no_nendo) {
        this.checkFKConsistency(bajon_bangou, employee_mst, repoto_no_getsudo, repoto_no_nendo);
        this.pk = new Pk(bajon_bangou, employee_mst.getEmp_code(), repoto_no_getsudo, repoto_no_nendo);
        this.bajon_bangou = bajon_bangou;
        this.employee_mst = employee_mst;
        this.repoto_no_getsudo = repoto_no_getsudo;
        this.repoto_no_nendo = repoto_no_nendo;
        this.shain_kodo = employee_mst.getEmp_code();
    }

    private void checkFKConsistency(int bajon_bangou, arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, int repoto_no_getsudo, int repoto_no_nendo) {
        if (employee_mst == null) {
            throw new IllegalArgumentException("Parameter employee_mst must not be null");
        }
    }

    //Primary Key
    //Should be initialized only once by the constructor, thus there's no setters
    @EmbeddedId
    protected Pk pk;
    @Override
    public Pk getPk() {
        return this.pk;
    }

    //PK columns
    //Should have insertable=false, updatable=false, and no setters

    @Column(insertable = false, updatable = false)
    protected int bajon_bangou;

    public int getBajon_bangou() {
        return this.bajon_bangou;
    }

    @Column(insertable = false, updatable = false)
    protected int shain_kodo;

    public int getShain_kodo() {
        return this.shain_kodo;
    }

    @Column(insertable = false, updatable = false)
    protected int repoto_no_getsudo;

    public int getRepoto_no_getsudo() {
        return this.repoto_no_getsudo;
    }

    @Column(insertable = false, updatable = false)
    protected int repoto_no_nendo;

    public int getRepoto_no_nendo() {
        return this.repoto_no_nendo;
    }

    //Normal columns

    @Column
    private java.util.Date chokkin_no_henshuu_bi;

    public java.util.Date getChokkin_no_henshuu_bi() {
        return this.chokkin_no_henshuu_bi;
    }

    public final java.util.Date getChokkin_no_henshuu_bi_DIRECT() {
        return this.chokkin_no_henshuu_bi;
    }

    public void setChokkin_no_henshuu_bi(final java.util.Date chokkin_no_henshuu_bi) {
        this.chokkin_no_henshuu_bi = chokkin_no_henshuu_bi;
    }

    public final void setChokkin_no_henshuu_bi_DIRECT(final java.util.Date chokkin_no_henshuu_bi) {
        this.chokkin_no_henshuu_bi = chokkin_no_henshuu_bi;
    }

    @Column
    private java.lang.Double genka_ritsu;

    public java.lang.Double getGenka_ritsu() {
        return this.genka_ritsu;
    }

    public final java.lang.Double getGenka_ritsu_DIRECT() {
        return this.genka_ritsu;
    }

    public void setGenka_ritsu(final java.lang.Double genka_ritsu) {
        this.genka_ritsu = genka_ritsu;
    }

    public final void setGenka_ritsu_DIRECT(final java.lang.Double genka_ritsu) {
        this.genka_ritsu = genka_ritsu;
    }

    @Column
    private java.lang.String henshuu_riyuu;

    public java.lang.String getHenshuu_riyuu() {
        return this.henshuu_riyuu;
    }

    public final java.lang.String getHenshuu_riyuu_DIRECT() {
        return this.henshuu_riyuu;
    }

    public void setHenshuu_riyuu(final java.lang.String henshuu_riyuu) {
        this.henshuu_riyuu = henshuu_riyuu;
    }

    public final void setHenshuu_riyuu_DIRECT(final java.lang.String henshuu_riyuu) {
        this.henshuu_riyuu = henshuu_riyuu;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_gijutsusha_shoukai;

    public java.lang.Integer getHoumon_kensuu_mokuteki_gijutsusha_shoukai() {
        return this.houmon_kensuu_mokuteki_gijutsusha_shoukai;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_gijutsusha_shoukai_DIRECT() {
        return this.houmon_kensuu_mokuteki_gijutsusha_shoukai;
    }

    public void setHoumon_kensuu_mokuteki_gijutsusha_shoukai(final java.lang.Integer houmon_kensuu_mokuteki_gijutsusha_shoukai) {
        this.houmon_kensuu_mokuteki_gijutsusha_shoukai = houmon_kensuu_mokuteki_gijutsusha_shoukai;
    }

    public final void setHoumon_kensuu_mokuteki_gijutsusha_shoukai_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_gijutsusha_shoukai) {
        this.houmon_kensuu_mokuteki_gijutsusha_shoukai = houmon_kensuu_mokuteki_gijutsusha_shoukai;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_gyouda;

    public java.lang.Integer getHoumon_kensuu_mokuteki_gyouda() {
        return this.houmon_kensuu_mokuteki_gyouda;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_gyouda_DIRECT() {
        return this.houmon_kensuu_mokuteki_gyouda;
    }

    public void setHoumon_kensuu_mokuteki_gyouda(final java.lang.Integer houmon_kensuu_mokuteki_gyouda) {
        this.houmon_kensuu_mokuteki_gyouda = houmon_kensuu_mokuteki_gyouda;
    }

    public final void setHoumon_kensuu_mokuteki_gyouda_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_gyouda) {
        this.houmon_kensuu_mokuteki_gyouda = houmon_kensuu_mokuteki_gyouda;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon;

    public java.lang.Integer getHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon() {
        return this.houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon_DIRECT() {
        return this.houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon;
    }

    public void setHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon(final java.lang.Integer houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon) {
        this.houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon = houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon;
    }

    public final void setHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon) {
        this.houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon = houmon_kensuu_mokuteki_kizon_igai_no_teiki_houmon;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_kizon_no_teiki_houmon;

    public java.lang.Integer getHoumon_kensuu_mokuteki_kizon_no_teiki_houmon() {
        return this.houmon_kensuu_mokuteki_kizon_no_teiki_houmon;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_kizon_no_teiki_houmon_DIRECT() {
        return this.houmon_kensuu_mokuteki_kizon_no_teiki_houmon;
    }

    public void setHoumon_kensuu_mokuteki_kizon_no_teiki_houmon(final java.lang.Integer houmon_kensuu_mokuteki_kizon_no_teiki_houmon) {
        this.houmon_kensuu_mokuteki_kizon_no_teiki_houmon = houmon_kensuu_mokuteki_kizon_no_teiki_houmon;
    }

    public final void setHoumon_kensuu_mokuteki_kizon_no_teiki_houmon_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_kizon_no_teiki_houmon) {
        this.houmon_kensuu_mokuteki_kizon_no_teiki_houmon = houmon_kensuu_mokuteki_kizon_no_teiki_houmon;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_kuremu;

    public java.lang.Integer getHoumon_kensuu_mokuteki_kuremu() {
        return this.houmon_kensuu_mokuteki_kuremu;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_kuremu_DIRECT() {
        return this.houmon_kensuu_mokuteki_kuremu;
    }

    public void setHoumon_kensuu_mokuteki_kuremu(final java.lang.Integer houmon_kensuu_mokuteki_kuremu) {
        this.houmon_kensuu_mokuteki_kuremu = houmon_kensuu_mokuteki_kuremu;
    }

    public final void setHoumon_kensuu_mokuteki_kuremu_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_kuremu) {
        this.houmon_kensuu_mokuteki_kuremu = houmon_kensuu_mokuteki_kuremu;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_mitsumori;

    public java.lang.Integer getHoumon_kensuu_mokuteki_mitsumori() {
        return this.houmon_kensuu_mokuteki_mitsumori;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_mitsumori_DIRECT() {
        return this.houmon_kensuu_mokuteki_mitsumori;
    }

    public void setHoumon_kensuu_mokuteki_mitsumori(final java.lang.Integer houmon_kensuu_mokuteki_mitsumori) {
        this.houmon_kensuu_mokuteki_mitsumori = houmon_kensuu_mokuteki_mitsumori;
    }

    public final void setHoumon_kensuu_mokuteki_mitsumori_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_mitsumori) {
        this.houmon_kensuu_mokuteki_mitsumori = houmon_kensuu_mokuteki_mitsumori;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_shinkihoumon;

    public java.lang.Integer getHoumon_kensuu_mokuteki_shinkihoumon() {
        return this.houmon_kensuu_mokuteki_shinkihoumon;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_shinkihoumon_DIRECT() {
        return this.houmon_kensuu_mokuteki_shinkihoumon;
    }

    public void setHoumon_kensuu_mokuteki_shinkihoumon(final java.lang.Integer houmon_kensuu_mokuteki_shinkihoumon) {
        this.houmon_kensuu_mokuteki_shinkihoumon = houmon_kensuu_mokuteki_shinkihoumon;
    }

    public final void setHoumon_kensuu_mokuteki_shinkihoumon_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_shinkihoumon) {
        this.houmon_kensuu_mokuteki_shinkihoumon = houmon_kensuu_mokuteki_shinkihoumon;
    }

    @Column
    private java.lang.Integer houmon_kensuu_mokuteki_sonota;

    public java.lang.Integer getHoumon_kensuu_mokuteki_sonota() {
        return this.houmon_kensuu_mokuteki_sonota;
    }

    public final java.lang.Integer getHoumon_kensuu_mokuteki_sonota_DIRECT() {
        return this.houmon_kensuu_mokuteki_sonota;
    }

    public void setHoumon_kensuu_mokuteki_sonota(final java.lang.Integer houmon_kensuu_mokuteki_sonota) {
        this.houmon_kensuu_mokuteki_sonota = houmon_kensuu_mokuteki_sonota;
    }

    public final void setHoumon_kensuu_mokuteki_sonota_DIRECT(final java.lang.Integer houmon_kensuu_mokuteki_sonota) {
        this.houmon_kensuu_mokuteki_sonota = houmon_kensuu_mokuteki_sonota;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_denki;

    public java.lang.Integer getHoumon_kensuu_shokushu_denki() {
        return this.houmon_kensuu_shokushu_denki;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_denki_DIRECT() {
        return this.houmon_kensuu_shokushu_denki;
    }

    public void setHoumon_kensuu_shokushu_denki(final java.lang.Integer houmon_kensuu_shokushu_denki) {
        this.houmon_kensuu_shokushu_denki = houmon_kensuu_shokushu_denki;
    }

    public final void setHoumon_kensuu_shokushu_denki_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_denki) {
        this.houmon_kensuu_shokushu_denki = houmon_kensuu_shokushu_denki;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_doboku;

    public java.lang.Integer getHoumon_kensuu_shokushu_doboku() {
        return this.houmon_kensuu_shokushu_doboku;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_doboku_DIRECT() {
        return this.houmon_kensuu_shokushu_doboku;
    }

    public void setHoumon_kensuu_shokushu_doboku(final java.lang.Integer houmon_kensuu_shokushu_doboku) {
        this.houmon_kensuu_shokushu_doboku = houmon_kensuu_shokushu_doboku;
    }

    public final void setHoumon_kensuu_shokushu_doboku_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_doboku) {
        this.houmon_kensuu_shokushu_doboku = houmon_kensuu_shokushu_doboku;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_ippan;

    public java.lang.Integer getHoumon_kensuu_shokushu_ippan() {
        return this.houmon_kensuu_shokushu_ippan;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_ippan_DIRECT() {
        return this.houmon_kensuu_shokushu_ippan;
    }

    public void setHoumon_kensuu_shokushu_ippan(final java.lang.Integer houmon_kensuu_shokushu_ippan) {
        this.houmon_kensuu_shokushu_ippan = houmon_kensuu_shokushu_ippan;
    }

    public final void setHoumon_kensuu_shokushu_ippan_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_ippan) {
        this.houmon_kensuu_shokushu_ippan = houmon_kensuu_shokushu_ippan;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_jimu;

    public java.lang.Integer getHoumon_kensuu_shokushu_jimu() {
        return this.houmon_kensuu_shokushu_jimu;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_jimu_DIRECT() {
        return this.houmon_kensuu_shokushu_jimu;
    }

    public void setHoumon_kensuu_shokushu_jimu(final java.lang.Integer houmon_kensuu_shokushu_jimu) {
        this.houmon_kensuu_shokushu_jimu = houmon_kensuu_shokushu_jimu;
    }

    public final void setHoumon_kensuu_shokushu_jimu_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_jimu) {
        this.houmon_kensuu_shokushu_jimu = houmon_kensuu_shokushu_jimu;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_kenchiku;

    public java.lang.Integer getHoumon_kensuu_shokushu_kenchiku() {
        return this.houmon_kensuu_shokushu_kenchiku;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_kenchiku_DIRECT() {
        return this.houmon_kensuu_shokushu_kenchiku;
    }

    public void setHoumon_kensuu_shokushu_kenchiku(final java.lang.Integer houmon_kensuu_shokushu_kenchiku) {
        this.houmon_kensuu_shokushu_kenchiku = houmon_kensuu_shokushu_kenchiku;
    }

    public final void setHoumon_kensuu_shokushu_kenchiku_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_kenchiku) {
        this.houmon_kensuu_shokushu_kenchiku = houmon_kensuu_shokushu_kenchiku;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_kikai_hado;

    public java.lang.Integer getHoumon_kensuu_shokushu_kikai_hado() {
        return this.houmon_kensuu_shokushu_kikai_hado;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_kikai_hado_DIRECT() {
        return this.houmon_kensuu_shokushu_kikai_hado;
    }

    public void setHoumon_kensuu_shokushu_kikai_hado(final java.lang.Integer houmon_kensuu_shokushu_kikai_hado) {
        this.houmon_kensuu_shokushu_kikai_hado = houmon_kensuu_shokushu_kikai_hado;
    }

    public final void setHoumon_kensuu_shokushu_kikai_hado_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_kikai_hado) {
        this.houmon_kensuu_shokushu_kikai_hado = houmon_kensuu_shokushu_kikai_hado;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_koru_centa;

    public java.lang.Integer getHoumon_kensuu_shokushu_koru_centa() {
        return this.houmon_kensuu_shokushu_koru_centa;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_koru_centa_DIRECT() {
        return this.houmon_kensuu_shokushu_koru_centa;
    }

    public void setHoumon_kensuu_shokushu_koru_centa(final java.lang.Integer houmon_kensuu_shokushu_koru_centa) {
        this.houmon_kensuu_shokushu_koru_centa = houmon_kensuu_shokushu_koru_centa;
    }

    public final void setHoumon_kensuu_shokushu_koru_centa_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_koru_centa) {
        this.houmon_kensuu_shokushu_koru_centa = houmon_kensuu_shokushu_koru_centa;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_netto_waku;

    public java.lang.Integer getHoumon_kensuu_shokushu_netto_waku() {
        return this.houmon_kensuu_shokushu_netto_waku;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_netto_waku_DIRECT() {
        return this.houmon_kensuu_shokushu_netto_waku;
    }

    public void setHoumon_kensuu_shokushu_netto_waku(final java.lang.Integer houmon_kensuu_shokushu_netto_waku) {
        this.houmon_kensuu_shokushu_netto_waku = houmon_kensuu_shokushu_netto_waku;
    }

    public final void setHoumon_kensuu_shokushu_netto_waku_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_netto_waku) {
        this.houmon_kensuu_shokushu_netto_waku = houmon_kensuu_shokushu_netto_waku;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_puranto;

    public java.lang.Integer getHoumon_kensuu_shokushu_puranto() {
        return this.houmon_kensuu_shokushu_puranto;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_puranto_DIRECT() {
        return this.houmon_kensuu_shokushu_puranto;
    }

    public void setHoumon_kensuu_shokushu_puranto(final java.lang.Integer houmon_kensuu_shokushu_puranto) {
        this.houmon_kensuu_shokushu_puranto = houmon_kensuu_shokushu_puranto;
    }

    public final void setHoumon_kensuu_shokushu_puranto_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_puranto) {
        this.houmon_kensuu_shokushu_puranto = houmon_kensuu_shokushu_puranto;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_setsubi;

    public java.lang.Integer getHoumon_kensuu_shokushu_setsubi() {
        return this.houmon_kensuu_shokushu_setsubi;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_setsubi_DIRECT() {
        return this.houmon_kensuu_shokushu_setsubi;
    }

    public void setHoumon_kensuu_shokushu_setsubi(final java.lang.Integer houmon_kensuu_shokushu_setsubi) {
        this.houmon_kensuu_shokushu_setsubi = houmon_kensuu_shokushu_setsubi;
    }

    public final void setHoumon_kensuu_shokushu_setsubi_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_setsubi) {
        this.houmon_kensuu_shokushu_setsubi = houmon_kensuu_shokushu_setsubi;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_sofuto_wea;

    public java.lang.Integer getHoumon_kensuu_shokushu_sofuto_wea() {
        return this.houmon_kensuu_shokushu_sofuto_wea;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_sofuto_wea_DIRECT() {
        return this.houmon_kensuu_shokushu_sofuto_wea;
    }

    public void setHoumon_kensuu_shokushu_sofuto_wea(final java.lang.Integer houmon_kensuu_shokushu_sofuto_wea) {
        this.houmon_kensuu_shokushu_sofuto_wea = houmon_kensuu_shokushu_sofuto_wea;
    }

    public final void setHoumon_kensuu_shokushu_sofuto_wea_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_sofuto_wea) {
        this.houmon_kensuu_shokushu_sofuto_wea = houmon_kensuu_shokushu_sofuto_wea;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_tsuushin_musen;

    public java.lang.Integer getHoumon_kensuu_shokushu_tsuushin_musen() {
        return this.houmon_kensuu_shokushu_tsuushin_musen;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_tsuushin_musen_DIRECT() {
        return this.houmon_kensuu_shokushu_tsuushin_musen;
    }

    public void setHoumon_kensuu_shokushu_tsuushin_musen(final java.lang.Integer houmon_kensuu_shokushu_tsuushin_musen) {
        this.houmon_kensuu_shokushu_tsuushin_musen = houmon_kensuu_shokushu_tsuushin_musen;
    }

    public final void setHoumon_kensuu_shokushu_tsuushin_musen_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_tsuushin_musen) {
        this.houmon_kensuu_shokushu_tsuushin_musen = houmon_kensuu_shokushu_tsuushin_musen;
    }

    @Column
    private java.lang.Integer houmon_kensuu_shokushu_tsuushin_yuusen;

    public java.lang.Integer getHoumon_kensuu_shokushu_tsuushin_yuusen() {
        return this.houmon_kensuu_shokushu_tsuushin_yuusen;
    }

    public final java.lang.Integer getHoumon_kensuu_shokushu_tsuushin_yuusen_DIRECT() {
        return this.houmon_kensuu_shokushu_tsuushin_yuusen;
    }

    public void setHoumon_kensuu_shokushu_tsuushin_yuusen(final java.lang.Integer houmon_kensuu_shokushu_tsuushin_yuusen) {
        this.houmon_kensuu_shokushu_tsuushin_yuusen = houmon_kensuu_shokushu_tsuushin_yuusen;
    }

    public final void setHoumon_kensuu_shokushu_tsuushin_yuusen_DIRECT(final java.lang.Integer houmon_kensuu_shokushu_tsuushin_yuusen) {
        this.houmon_kensuu_shokushu_tsuushin_yuusen = houmon_kensuu_shokushu_tsuushin_yuusen;
    }

    @Column
    private java.lang.String houmon_saki_kigyou_ichiran;

    public java.lang.String getHoumon_saki_kigyou_ichiran() {
        return this.houmon_saki_kigyou_ichiran;
    }

    public final java.lang.String getHoumon_saki_kigyou_ichiran_DIRECT() {
        return this.houmon_saki_kigyou_ichiran;
    }

    public void setHoumon_saki_kigyou_ichiran(final java.lang.String houmon_saki_kigyou_ichiran) {
        this.houmon_saki_kigyou_ichiran = houmon_saki_kigyou_ichiran;
    }

    public final void setHoumon_saki_kigyou_ichiran_DIRECT(final java.lang.String houmon_saki_kigyou_ichiran) {
        this.houmon_saki_kigyou_ichiran = houmon_saki_kigyou_ichiran;
    }

    @Column
    private java.lang.Integer ichiji_mensetsu_no_kensuu;

    public java.lang.Integer getIchiji_mensetsu_no_kensuu() {
        return this.ichiji_mensetsu_no_kensuu;
    }

    public final java.lang.Integer getIchiji_mensetsu_no_kensuu_DIRECT() {
        return this.ichiji_mensetsu_no_kensuu;
    }

    public void setIchiji_mensetsu_no_kensuu(final java.lang.Integer ichiji_mensetsu_no_kensuu) {
        this.ichiji_mensetsu_no_kensuu = ichiji_mensetsu_no_kensuu;
    }

    public final void setIchiji_mensetsu_no_kensuu_DIRECT(final java.lang.Integer ichiji_mensetsu_no_kensuu) {
        this.ichiji_mensetsu_no_kensuu = ichiji_mensetsu_no_kensuu;
    }

    @Column
    private java.lang.String jigetsuno_mokuhyou;

    public java.lang.String getJigetsuno_mokuhyou() {
        return this.jigetsuno_mokuhyou;
    }

    public final java.lang.String getJigetsuno_mokuhyou_DIRECT() {
        return this.jigetsuno_mokuhyou;
    }

    public void setJigetsuno_mokuhyou(final java.lang.String jigetsuno_mokuhyou) {
        this.jigetsuno_mokuhyou = jigetsuno_mokuhyou;
    }

    public final void setJigetsuno_mokuhyou_DIRECT(final java.lang.String jigetsuno_mokuhyou) {
        this.jigetsuno_mokuhyou = jigetsuno_mokuhyou;
    }

    @Column
    private java.lang.Integer jigetsu_tsuitachi_kara_no_haken_shain_suu;

    public java.lang.Integer getJigetsu_tsuitachi_kara_no_haken_shain_suu() {
        return this.jigetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    public final java.lang.Integer getJigetsu_tsuitachi_kara_no_haken_shain_suu_DIRECT() {
        return this.jigetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    public void setJigetsu_tsuitachi_kara_no_haken_shain_suu(final java.lang.Integer jigetsu_tsuitachi_kara_no_haken_shain_suu) {
        this.jigetsu_tsuitachi_kara_no_haken_shain_suu = jigetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    public final void setJigetsu_tsuitachi_kara_no_haken_shain_suu_DIRECT(final java.lang.Integer jigetsu_tsuitachi_kara_no_haken_shain_suu) {
        this.jigetsu_tsuitachi_kara_no_haken_shain_suu = jigetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    @Column
    private java.lang.Integer kadou_nissuu;

    public java.lang.Integer getKadou_nissuu() {
        return this.kadou_nissuu;
    }

    public final java.lang.Integer getKadou_nissuu_DIRECT() {
        return this.kadou_nissuu;
    }

    public void setKadou_nissuu(final java.lang.Integer kadou_nissuu) {
        this.kadou_nissuu = kadou_nissuu;
    }

    public final void setKadou_nissuu_DIRECT(final java.lang.Integer kadou_nissuu) {
        this.kadou_nissuu = kadou_nissuu;
    }

    @Column
    private java.lang.Integer niji_mensetsu_no_kensuu;

    public java.lang.Integer getNiji_mensetsu_no_kensuu() {
        return this.niji_mensetsu_no_kensuu;
    }

    public final java.lang.Integer getNiji_mensetsu_no_kensuu_DIRECT() {
        return this.niji_mensetsu_no_kensuu;
    }

    public void setNiji_mensetsu_no_kensuu(final java.lang.Integer niji_mensetsu_no_kensuu) {
        this.niji_mensetsu_no_kensuu = niji_mensetsu_no_kensuu;
    }

    public final void setNiji_mensetsu_no_kensuu_DIRECT(final java.lang.Integer niji_mensetsu_no_kensuu) {
        this.niji_mensetsu_no_kensuu = niji_mensetsu_no_kensuu;
    }

    @Column
    private java.lang.String no_kekka_ni_kansuru_genin;

    public java.lang.String getNo_kekka_ni_kansuru_genin() {
        return this.no_kekka_ni_kansuru_genin;
    }

    public final java.lang.String getNo_kekka_ni_kansuru_genin_DIRECT() {
        return this.no_kekka_ni_kansuru_genin;
    }

    public void setNo_kekka_ni_kansuru_genin(final java.lang.String no_kekka_ni_kansuru_genin) {
        this.no_kekka_ni_kansuru_genin = no_kekka_ni_kansuru_genin;
    }

    public final void setNo_kekka_ni_kansuru_genin_DIRECT(final java.lang.String no_kekka_ni_kansuru_genin) {
        this.no_kekka_ni_kansuru_genin = no_kekka_ni_kansuru_genin;
    }

    @Column
    private java.lang.Integer nyuryoku_nissuu;

    public java.lang.Integer getNyuryoku_nissuu() {
        return this.nyuryoku_nissuu;
    }

    public final java.lang.Integer getNyuryoku_nissuu_DIRECT() {
        return this.nyuryoku_nissuu;
    }

    public void setNyuryoku_nissuu(final java.lang.Integer nyuryoku_nissuu) {
        this.nyuryoku_nissuu = nyuryoku_nissuu;
    }

    public final void setNyuryoku_nissuu_DIRECT(final java.lang.Integer nyuryoku_nissuu) {
        this.nyuryoku_nissuu = nyuryoku_nissuu;
    }

    @Column
    private boolean repoto_no_ribijon_no_sakujo_furagu;

    public boolean getRepoto_no_ribijon_no_sakujo_furagu() {
        return this.repoto_no_ribijon_no_sakujo_furagu;
    }

    public final boolean getRepoto_no_ribijon_no_sakujo_furagu_DIRECT() {
        return this.repoto_no_ribijon_no_sakujo_furagu;
    }

    public void setRepoto_no_ribijon_no_sakujo_furagu(final boolean repoto_no_ribijon_no_sakujo_furagu) {
        this.repoto_no_ribijon_no_sakujo_furagu = repoto_no_ribijon_no_sakujo_furagu;
    }

    public final void setRepoto_no_ribijon_no_sakujo_furagu_DIRECT(final boolean repoto_no_ribijon_no_sakujo_furagu) {
        this.repoto_no_ribijon_no_sakujo_furagu = repoto_no_ribijon_no_sakujo_furagu;
    }

    @Column
    private java.lang.Integer saiyou_sapoto_ninzuu;

    public java.lang.Integer getSaiyou_sapoto_ninzuu() {
        return this.saiyou_sapoto_ninzuu;
    }

    public final java.lang.Integer getSaiyou_sapoto_ninzuu_DIRECT() {
        return this.saiyou_sapoto_ninzuu;
    }

    public void setSaiyou_sapoto_ninzuu(final java.lang.Integer saiyou_sapoto_ninzuu) {
        this.saiyou_sapoto_ninzuu = saiyou_sapoto_ninzuu;
    }

    public final void setSaiyou_sapoto_ninzuu_DIRECT(final java.lang.Integer saiyou_sapoto_ninzuu) {
        this.saiyou_sapoto_ninzuu = saiyou_sapoto_ninzuu;
    }

    @Column
    private java.lang.Integer saiyou_suu;

    public java.lang.Integer getSaiyou_suu() {
        return this.saiyou_suu;
    }

    public final java.lang.Integer getSaiyou_suu_DIRECT() {
        return this.saiyou_suu;
    }

    public void setSaiyou_suu(final java.lang.Integer saiyou_suu) {
        this.saiyou_suu = saiyou_suu;
    }

    public final void setSaiyou_suu_DIRECT(final java.lang.Integer saiyou_suu) {
        this.saiyou_suu = saiyou_suu;
    }

    @Column
    private java.lang.Integer shinki_kaitaku_suu;

    public java.lang.Integer getShinki_kaitaku_suu() {
        return this.shinki_kaitaku_suu;
    }

    public final java.lang.Integer getShinki_kaitaku_suu_DIRECT() {
        return this.shinki_kaitaku_suu;
    }

    public void setShinki_kaitaku_suu(final java.lang.Integer shinki_kaitaku_suu) {
        this.shinki_kaitaku_suu = shinki_kaitaku_suu;
    }

    public final void setShinki_kaitaku_suu_DIRECT(final java.lang.Integer shinki_kaitaku_suu) {
        this.shinki_kaitaku_suu = shinki_kaitaku_suu;
    }

    @Column
    private java.lang.String shochou_no_soukatsu;

    public java.lang.String getShochou_no_soukatsu() {
        return this.shochou_no_soukatsu;
    }

    public final java.lang.String getShochou_no_soukatsu_DIRECT() {
        return this.shochou_no_soukatsu;
    }

    public void setShochou_no_soukatsu(final java.lang.String shochou_no_soukatsu) {
        this.shochou_no_soukatsu = shochou_no_soukatsu;
    }

    public final void setShochou_no_soukatsu_DIRECT(final java.lang.String shochou_no_soukatsu) {
        this.shochou_no_soukatsu = shochou_no_soukatsu;
    }

    @Column
    private java.lang.Integer shouninsha_kodo;

    public java.lang.Integer getShouninsha_kodo() {
        return this.shouninsha_kodo;
    }

    public final java.lang.Integer getShouninsha_kodo_DIRECT() {
        return this.shouninsha_kodo;
    }

    public void setShouninsha_kodo(final java.lang.Integer shouninsha_kodo) {
        this.shouninsha_kodo = shouninsha_kodo;
    }

    public final void setShouninsha_kodo_DIRECT(final java.lang.Integer shouninsha_kodo) {
        this.shouninsha_kodo = shouninsha_kodo;
    }

    @Column
    private java.lang.String shounin_joutai;

    public java.lang.String getShounin_joutai() {
        return this.shounin_joutai;
    }

    public final java.lang.String getShounin_joutai_DIRECT() {
        return this.shounin_joutai;
    }

    public void setShounin_joutai(final java.lang.String shounin_joutai) {
        this.shounin_joutai = shounin_joutai;
    }

    public final void setShounin_joutai_DIRECT(final java.lang.String shounin_joutai) {
        this.shounin_joutai = shounin_joutai;
    }

    @Column
    private java.lang.String sono_genin_ga_shoujita_riyuu;

    public java.lang.String getSono_genin_ga_shoujita_riyuu() {
        return this.sono_genin_ga_shoujita_riyuu;
    }

    public final java.lang.String getSono_genin_ga_shoujita_riyuu_DIRECT() {
        return this.sono_genin_ga_shoujita_riyuu;
    }

    public void setSono_genin_ga_shoujita_riyuu(final java.lang.String sono_genin_ga_shoujita_riyuu) {
        this.sono_genin_ga_shoujita_riyuu = sono_genin_ga_shoujita_riyuu;
    }

    public final void setSono_genin_ga_shoujita_riyuu_DIRECT(final java.lang.String sono_genin_ga_shoujita_riyuu) {
        this.sono_genin_ga_shoujita_riyuu = sono_genin_ga_shoujita_riyuu;
    }

    @Column
    private java.lang.Integer sou_houmon_kensuu;

    public java.lang.Integer getSou_houmon_kensuu() {
        return this.sou_houmon_kensuu;
    }

    public final java.lang.Integer getSou_houmon_kensuu_DIRECT() {
        return this.sou_houmon_kensuu;
    }

    public void setSou_houmon_kensuu(final java.lang.Integer sou_houmon_kensuu) {
        this.sou_houmon_kensuu = sou_houmon_kensuu;
    }

    public final void setSou_houmon_kensuu_DIRECT(final java.lang.Integer sou_houmon_kensuu) {
        this.sou_houmon_kensuu = sou_houmon_kensuu;
    }

    @Column
    private java.lang.Integer sutaffu_kanri_denwa_forosuu;

    public java.lang.Integer getSutaffu_kanri_denwa_forosuu() {
        return this.sutaffu_kanri_denwa_forosuu;
    }

    public final java.lang.Integer getSutaffu_kanri_denwa_forosuu_DIRECT() {
        return this.sutaffu_kanri_denwa_forosuu;
    }

    public void setSutaffu_kanri_denwa_forosuu(final java.lang.Integer sutaffu_kanri_denwa_forosuu) {
        this.sutaffu_kanri_denwa_forosuu = sutaffu_kanri_denwa_forosuu;
    }

    public final void setSutaffu_kanri_denwa_forosuu_DIRECT(final java.lang.Integer sutaffu_kanri_denwa_forosuu) {
        this.sutaffu_kanri_denwa_forosuu = sutaffu_kanri_denwa_forosuu;
    }

    @Column
    private java.lang.Integer sutaffu_kanri_kuremu_suu;

    public java.lang.Integer getSutaffu_kanri_kuremu_suu() {
        return this.sutaffu_kanri_kuremu_suu;
    }

    public final java.lang.Integer getSutaffu_kanri_kuremu_suu_DIRECT() {
        return this.sutaffu_kanri_kuremu_suu;
    }

    public void setSutaffu_kanri_kuremu_suu(final java.lang.Integer sutaffu_kanri_kuremu_suu) {
        this.sutaffu_kanri_kuremu_suu = sutaffu_kanri_kuremu_suu;
    }

    public final void setSutaffu_kanri_kuremu_suu_DIRECT(final java.lang.Integer sutaffu_kanri_kuremu_suu) {
        this.sutaffu_kanri_kuremu_suu = sutaffu_kanri_kuremu_suu;
    }

    @Column
    private java.lang.Integer sutaffu_kanri_mensetsu_forosuu;

    public java.lang.Integer getSutaffu_kanri_mensetsu_forosuu() {
        return this.sutaffu_kanri_mensetsu_forosuu;
    }

    public final java.lang.Integer getSutaffu_kanri_mensetsu_forosuu_DIRECT() {
        return this.sutaffu_kanri_mensetsu_forosuu;
    }

    public void setSutaffu_kanri_mensetsu_forosuu(final java.lang.Integer sutaffu_kanri_mensetsu_forosuu) {
        this.sutaffu_kanri_mensetsu_forosuu = sutaffu_kanri_mensetsu_forosuu;
    }

    public final void setSutaffu_kanri_mensetsu_forosuu_DIRECT(final java.lang.Integer sutaffu_kanri_mensetsu_forosuu) {
        this.sutaffu_kanri_mensetsu_forosuu = sutaffu_kanri_mensetsu_forosuu;
    }

    @Column
    private java.lang.Integer sutaffu_kanri_sono_ta_no_kazu;

    public java.lang.Integer getSutaffu_kanri_sono_ta_no_kazu() {
        return this.sutaffu_kanri_sono_ta_no_kazu;
    }

    public final java.lang.Integer getSutaffu_kanri_sono_ta_no_kazu_DIRECT() {
        return this.sutaffu_kanri_sono_ta_no_kazu;
    }

    public void setSutaffu_kanri_sono_ta_no_kazu(final java.lang.Integer sutaffu_kanri_sono_ta_no_kazu) {
        this.sutaffu_kanri_sono_ta_no_kazu = sutaffu_kanri_sono_ta_no_kazu;
    }

    public final void setSutaffu_kanri_sono_ta_no_kazu_DIRECT(final java.lang.Integer sutaffu_kanri_sono_ta_no_kazu) {
        this.sutaffu_kanri_sono_ta_no_kazu = sutaffu_kanri_sono_ta_no_kazu;
    }

    @Column
    private java.lang.Integer tougetsu_tsuitachi_kara_no_haken_shain_suu;

    public java.lang.Integer getTougetsu_tsuitachi_kara_no_haken_shain_suu() {
        return this.tougetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    public final java.lang.Integer getTougetsu_tsuitachi_kara_no_haken_shain_suu_DIRECT() {
        return this.tougetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    public void setTougetsu_tsuitachi_kara_no_haken_shain_suu(final java.lang.Integer tougetsu_tsuitachi_kara_no_haken_shain_suu) {
        this.tougetsu_tsuitachi_kara_no_haken_shain_suu = tougetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    public final void setTougetsu_tsuitachi_kara_no_haken_shain_suu_DIRECT(final java.lang.Integer tougetsu_tsuitachi_kara_no_haken_shain_suu) {
        this.tougetsu_tsuitachi_kara_no_haken_shain_suu = tougetsu_tsuitachi_kara_no_haken_shain_suu;
    }

    @Column
    private java.lang.Integer uriage_ph;

    public java.lang.Integer getUriage_ph() {
        return this.uriage_ph;
    }

    public final java.lang.Integer getUriage_ph_DIRECT() {
        return this.uriage_ph;
    }

    public void setUriage_ph(final java.lang.Integer uriage_ph) {
        this.uriage_ph = uriage_ph;
    }

    public final void setUriage_ph_DIRECT(final java.lang.Integer uriage_ph) {
        this.uriage_ph = uriage_ph;
    }

    @Column
    private java.lang.Integer zouka_suu;

    public java.lang.Integer getZouka_suu() {
        return this.zouka_suu;
    }

    public final java.lang.Integer getZouka_suu_DIRECT() {
        return this.zouka_suu;
    }

    public void setZouka_suu(final java.lang.Integer zouka_suu) {
        this.zouka_suu = zouka_suu;
    }

    public final void setZouka_suu_DIRECT(final java.lang.Integer zouka_suu) {
        this.zouka_suu = zouka_suu;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "SHAIN_KODO", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Monthly_report_revision_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Monthly_report_revision_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.shain_kodo = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "SHOUNINSHA_KODO", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst manager;

    public arrow.businesstraceability.persistence.entity.Employee_mst getManager() {
        return this.manager;
    }

    /**
     * Set Manager for Monthly_report_revision_MAPPED.
     *
     * @param manager Employee_mst.
     *
     **/
    public void setManager(final arrow.businesstraceability.persistence.entity.Employee_mst manager) {
        if (manager == null) {
            this.shouninsha_kodo = null;
        }
        else {
            this.shouninsha_kodo = manager.getEmp_code();
        }
        this.manager = manager;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}