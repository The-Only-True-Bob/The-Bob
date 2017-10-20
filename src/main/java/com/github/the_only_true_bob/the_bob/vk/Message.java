package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public interface Message {
    // TODO: 20.10.17 Figure out how it should work and what it must consist

    MessageType type();

    String text();

    // TODO: 20.10.17 It must return somewhat
    void attachments();

    String userId();

    static Message from(final JsonObject body) {
        final JsonElement typeJson = body.get("type");
        final String type = typeJson.getAsString();
        return MessageType.of(type).parse(body).orElse(Message.empty());
    }

    static Message empty() {
        return new Message() {
            @Override
            public MessageType type() {
                return MessageType.UNASSIGNED;
            }

            @Override
            public String text() {
                return "";
            }

            @Override
            public void attachments() {
                //todo: Implement attachments
            }

            @Override
            public String userId() {
                return "UNASSIGNED";
            }
        };
    }

    static Builder builder() {
        return new Builder();
    }

    class Builder {
        private MessageType type;
        private String userVkId;
        private String text;
        private String pollId;
        private String optionId;
        private List<Attachment> attachments;

        private Builder() { }

        public Builder setType(MessageType type) {
            this.type = type;
            return this;
        }

        public Builder setUserVkId(String userVkId) {
            this.userVkId = userVkId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder setOptionId(String optionId) {
            this.optionId = optionId;
            return this;
        }

        public Builder setAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Message build() {
            return new Message() {
                @Override
                public MessageType type() {
                    return type;
                }

                @Override
                public String text() {
                    return text;
                }

                @Override
                public void attachments() {
                    //todo: Implement attachments
                }

                @Override
                public String userId() {
                    return userVkId;
                }
            };
        }
    }
}
