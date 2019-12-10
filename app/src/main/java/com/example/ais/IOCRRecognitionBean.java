package com.example.ais;

import java.util.List;

public class IOCRRecognitionBean {

    /**
     * data : {"ret":[{"probability":{"average":1,"min":1,"variance":0},"location":{"height":45,"left":333,"top":161,"width":95},"word_name":"规格型号","word":"N7900-1O"},{"probability":{"average":0.99989,"min":0.999406,"variance":0},"location":{"height":50,"left":192,"top":29,"width":374},"word_name":"中国移动固定资产标签","word":"中国移动海南固定资产标"},{"probability":{"average":0.998648,"min":0.985716,"variance":1.4E-5},"location":{"height":52,"left":207,"top":322,"width":370},"word_name":"条码","word":"2610-10277292"},{"probability":{"average":0.998752,"min":0.991928,"variance":6.0E-6},"location":{"height":34,"left":266,"top":96,"width":206},"word_name":"资产名称","word":"域网分组传送网设备"}],"templateSign":"e4ba3a58f23cf07e19274b665efc8118","templateName":"资产标签模板1","scores":1,"isStructured":true,"logId":"157570682594048","templateMatchDegree":0.981034075443789,"clockwiseAngle":354.72}
     * error_code : 0
     * error_msg :
     */

    private DataBean data;
    private int error_code;
    private String error_msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String toString() {
        return "data : {\n" + data.toString() + ",\nerror_code=" + error_code + ",\nerror_msg=" + error_msg + "\n }";
    }

    public static class DataBean {
        /**
         * ret : [{"probability":{"average":1,"min":1,"variance":0},"location":{"height":45,"left":333,"top":161,"width":95},"word_name":"规格型号","word":"N7900-1O"},{"probability":{"average":0.99989,"min":0.999406,"variance":0},"location":{"height":50,"left":192,"top":29,"width":374},"word_name":"中国移动固定资产标签","word":"中国移动海南固定资产标"},{"probability":{"average":0.998648,"min":0.985716,"variance":1.4E-5},"location":{"height":52,"left":207,"top":322,"width":370},"word_name":"条码","word":"2610-10277292"},{"probability":{"average":0.998752,"min":0.991928,"variance":6.0E-6},"location":{"height":34,"left":266,"top":96,"width":206},"word_name":"资产名称","word":"域网分组传送网设备"}]
         * templateSign : e4ba3a58f23cf07e19274b665efc8118
         * templateName : 资产标签模板1
         * scores : 1
         * isStructured : true
         * logId : 157570682594048
         * templateMatchDegree : 0.981034075443789
         * clockwiseAngle : 354.72
         */

        private String templateSign;
        private String templateName;
        private double scores;
        private boolean isStructured;
        private String logId;
        private double templateMatchDegree;
        private double clockwiseAngle;
        private List<RetBean> ret;

        public String getTemplateSign() {
            return templateSign;
        }

        public void setTemplateSign(String templateSign) {
            this.templateSign = templateSign;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public double getScores() {
            return scores;
        }

        public void setScores(double scores) {
            this.scores = scores;
        }

        public boolean isIsStructured() {
            return isStructured;
        }

        public void setIsStructured(boolean isStructured) {
            this.isStructured = isStructured;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public double getTemplateMatchDegree() {
            return templateMatchDegree;
        }

        public void setTemplateMatchDegree(double templateMatchDegree) {
            this.templateMatchDegree = templateMatchDegree;
        }

        public double getClockwiseAngle() {
            return clockwiseAngle;
        }

        public void setClockwiseAngle(double clockwiseAngle) {
            this.clockwiseAngle = clockwiseAngle;
        }

        public List<RetBean> getRet() {
            return ret;
        }

        public void setRet(List<RetBean> ret) {
            this.ret = ret;
        }

        public String toString() {
            String str = "";
            for(RetBean retBean : ret) {
                str = str + "{\n" + retBean.toString() + "}\n";
            }
            return "ret:[\n" + str + "]\n";
        }

        public static class RetBean {
            /**
             * probability : {"average":1,"min":1,"variance":0}
             * location : {"height":45,"left":333,"top":161,"width":95}
             * word_name : 规格型号
             * word : N7900-1O
             */

            private ProbabilityBean probability;
            private LocationBean location;
            private String word_name;
            private String word;

            public ProbabilityBean getProbability() {
                return probability;
            }

            public void setProbability(ProbabilityBean probability) {
                this.probability = probability;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getWord_name() {
                return word_name;
            }

            public void setWord_name(String word_name) {
                this.word_name = word_name;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public String toString() {
                return "word_name=" + word_name + "\n" + "word=" + word + "\n";
            }

            public static class ProbabilityBean {
                /**
                 * average : 1
                 * min : 1
                 * variance : 0
                 */

                private double average;
                private double min;
                private double variance;

                public double getAverage() {
                    return average;
                }

                public void setAverage(double average) {
                    this.average = average;
                }

                public double getMin() {
                    return min;
                }

                public void setMin(double min) {
                    this.min = min;
                }

                public double getVariance() {
                    return variance;
                }

                public void setVariance(double variance) {
                    this.variance = variance;
                }
            }

            public static class LocationBean {
                /**
                 * height : 45
                 * left : 333
                 * top : 161
                 * width : 95
                 */

                private int height;
                private int left;
                private int top;
                private int width;

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }
            }
        }
    }
}
