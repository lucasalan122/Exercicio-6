package com.xau.todoapp.util.time;

public class Data {
    private int dia;
    private int mes;
    private  int ano;

    /**
     * Cria um novo objeto data com dia, mes e ano
     * @param dia Dia
     * @param mes Mes
     * @param ano Ano
     * @throws DataInvalidaException Algum valor errado
     */
    public Data(int dia, int mes, int ano) throws DataInvalidaException {
        // Todos os parametros são setados pelos metodos por causa da verificação
        this.setDia(dia);
        this.setMes(mes);
        this.setAno(ano);
    }

    /**
     * Retorna o valor do dia
     * @return Dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * Retorna o valor do mes
     * @return Mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * Retorna o valor do ano
     * @return Ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * Formata uma data no formato dd/mm/aaaa
     * @param data Objeto Data criado
     * @return Data
     */
    public static Data formatData(String data) throws DataInvalidaException {
        //Divide a String pelo "/" e depois cria um objeto Data novo
        String[] datas = data.split("/");

        if (datas.length != 3) {
            throw new DataInvalidaException("Formato invalido");
        }

        int dia = Integer.parseInt(datas[0]);
        int mes = Integer.parseInt(datas[1]);
        int ano = Integer.parseInt(datas[2]);


        Data temp = new Data(dia, mes, ano);
        return temp;
    }

    /**
     * Retorna um String com a data no formato dd/mm/aaaa
     * @return data formatada
     */
    public String getFormatData() {
        StringBuilder data = new StringBuilder("");

        data.append(this.returnValueWithZero(this.dia, 2));
        data.append("/");
        data.append(this.returnValueWithZero(this.mes, 2));
        data.append("/");
        data.append(this.returnValueWithZero(this.ano, 4));

        return data.toString();
    }

    public String getFormatDataUniversal() {
        StringBuilder data = new StringBuilder("");

        data.append(this.returnValueWithZero(this.ano, 4));
        data.append("/");
        data.append(this.returnValueWithZero(this.mes, 2));
        data.append("/");
        data.append(this.returnValueWithZero(this.dia, 2));

        return data.toString();
    }

    /**
     * Adiciona zeros a frente dos numeros
     * @param number numero
     * @param offset tamanho usado para ser preenchido
     * @return Numero com zeros na frente
     */
    private String returnValueWithZero(int number, int offset) {
        StringBuilder prop = new StringBuilder("");
        int val = String.valueOf(number).length();
        for (int i = 0; i < offset - val; i++) {
            prop.append("0");
        }
        prop.append(number);

        return prop.toString();
    }

    /**
     * Seta o dia
     * @param dia dia
     * @throws DataInvalidaException Dia invalido
     */
    public void setDia(int dia) throws DataInvalidaException {
        if (String.valueOf(dia).length() > 2) {
            throw new DataInvalidaException("Formato do dia invalido");
        } else if (dia < 1 || dia > 31) {
            throw new DataInvalidaException("Dia invalido");
        }
        this.dia = dia;
    }

    /**
     * Seta o mes
     * @param mes Mes
     * @throws DataInvalidaException Mes invalido
     */
    public void setMes(int mes) throws DataInvalidaException {
        if (String.valueOf(mes).length() > 2) {
            throw new DataInvalidaException("Foramto do mes invalido");
        } else if (mes < 1 || mes > 12) {
            throw new DataInvalidaException("Mes invalido");
        }
        this.mes = mes;
    }

    /**
     * Seta o ano
     * @param ano Ano
     * @throws DataInvalidaException Ano invalido
     */
    public void setAno(int ano) throws DataInvalidaException {
        if (String.valueOf(ano).length() > 4) {
            throw new DataInvalidaException("Formato do dia invalido");
        } else if (ano < 1) {
            throw new DataInvalidaException("Ano invalido");
        }
        this.ano = ano;
    }
}
