package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface Message {
    MessageType type();
    Optional<String> text();
    Optional<String> pollId();
    Optional<String> optionId();
    List<Attachment> attachments();
    Optional<String> userId();

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
            public Optional<String> text() {
                return Optional.empty();
            }

            @Override
            public Optional<String> pollId() {
                return Optional.empty();
            }

            @Override
            public Optional<String> optionId() {
                return Optional.empty();
            }

            @Override
            public List<Attachment> attachments() {
                return Collections.emptyList();
            }

            @Override
            public Optional<String> userId() {
                return Optional.empty();
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
                public Optional<String> text() {
                    return Optional.ofNullable(text);
                }

                @Override
                public Optional<String> pollId() {
                    return Optional.ofNullable(pollId);
                }

                @Override
                public Optional<String> optionId() {
                    return Optional.ofNullable(optionId);
                }

                @Override
                public List<Attachment> attachments() {
                    return Optional.ofNullable(attachments).orElseGet(Collections::emptyList);
                }

                @Override
                public Optional<String> userId() {
                    return Optional.ofNullable(userVkId);
                }
            };
        }
    }
}
