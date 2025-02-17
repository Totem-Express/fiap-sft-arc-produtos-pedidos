package br.com.fiap.totem_express.domain.order;

public enum Status {
    RECEIVED(){
        @Override
        public Status next() {
            return PREPARING;
        }
    },
    PREPARING(){
        @Override
        public Status next() {
            return READY_TO_BE_PICKED_UP;
        }
    },
    READY_TO_BE_PICKED_UP(){
        @Override
        public Status next() {
            return FINISHED;
        }
    },
    FINISHED(){
        @Override
        public Status next() {
            return FINISHED;
        }
    };

    public abstract Status next();
}
